package com.jdaw.studentmanager.controller;

import com.jdaw.studentmanager.domain.Student;
import com.jdaw.studentmanager.service.ClazzService;
import com.jdaw.studentmanager.service.SelectedCourseService;
import com.jdaw.studentmanager.service.StudentService;
import com.jdaw.studentmanager.util.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.FileCopyUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.util.*;

/**
 * @Classname StudentController
 * @Description None
 * @Date 2023/11/25 20:00
 * @Created by jdaw
 */
@Controller
@RequestMapping("/student")
public class StudentController {

    @Autowired
    private StudentService studentService;
    @Autowired
    private ClazzService clazzService;
    @Autowired
    private SelectedCourseService selectedCourseService;

    /**
     * 跳转学生列表页面
     * @return
     */
    @GetMapping("/student_list")
    public String studentList(){
        return "/student/studentList";
    }

    /**
     * 异步加载学生列表
     * @param page
     * @param rows
     * @param studentName
     * @param clazzid
     * @param from
     * @param session
     * @return
     */
    @RequestMapping("/getStudentList")
    @ResponseBody
    public Object getStudentList(@RequestParam(value = "page", defaultValue = "1")Integer page,
                                 @RequestParam(value = "rows", defaultValue = "100")Integer rows,
                                 String studentName,
                                 @RequestParam(value = "clazzid", defaultValue = "0")String clazzid, String from, HttpSession session){
        Map<String,Object> paramMap = new HashMap();
        paramMap.put("pageno",page);
        paramMap.put("pagesize",rows);
        if(!StringUtils.isEmpty(studentName))  paramMap.put("username",studentName);
        if(!clazzid.equals("0"))  paramMap.put("clazzid",clazzid);

        //判断是老师还是学生权限
        Student student = (Student) session.getAttribute(Const.STUDENT);
        if(!StringUtils.isEmpty(student)){
            //是学生权限，只能查询自己的信息
            paramMap.put("studentid",student.getId());
        }

        PageBean<Student> pageBean = studentService.queryPage(paramMap);
        if(!StringUtils.isEmpty(from) && from.equals("combox")){
            return pageBean.getDatas();
        }else{
            Map<String,Object> result = new HashMap();
            result.put("total",pageBean.getTotalsize());
            result.put("rows",pageBean.getDatas());
            return result;
        }
    }

    /**
     * 删除学生
     * @param data
     * @return
     */
    @PostMapping("/deleteStudent")
    @ResponseBody
    public AjaxResult deleteStudent(Data data){
        AjaxResult ajaxResult = new AjaxResult();
        try {
            List<Integer> ids = data.getIds();
            Iterator<Integer> iterator = ids.iterator();
            while (iterator.hasNext()){  //判断是否存在课程关联学生
                if(!selectedCourseService.isStudentId(iterator.next())){
                    ajaxResult.setSuccess(false);
                    ajaxResult.setMessage("无法删除,存在课程关联学生");
                    return ajaxResult;
                }
            }
            File fileDir = UploadUtil.getImgDirFile();
            for(Integer id : ids){
                Student byId = studentService.findById(id);
                if(!byId.getPhoto().isEmpty()){
                    File file = new File(fileDir.getAbsolutePath() + File.separator + byId.getPhoto());
                    if(file != null){
                        file.delete();
                    }
                }
            }
            int count = studentService.deleteStudent(ids);
            if(count > 0){
                ajaxResult.setSuccess(true);
                ajaxResult.setMessage("全部删除成功");

            }else{
                ajaxResult.setSuccess(false);
                ajaxResult.setMessage("删除失败");
            }

        }catch (Exception e){
            e.printStackTrace();
            ajaxResult.setSuccess(false);
            ajaxResult.setMessage("删除失败");
        }
        return ajaxResult;
    }


    /**
     * 添加学生
     * @param files
     * @param student
     * @return
     * @throws IOException
     */
    @RequestMapping("/addStudent")
    @ResponseBody
    public AjaxResult addStudent(@RequestParam("file") MultipartFile[] files,Student student) throws IOException {

        AjaxResult ajaxResult = new AjaxResult();
        student.setSn(SnGenerateUtil.generateSn(student.getClazzId()));

        // 存放上传图片的文件夹
        File fileDir = UploadUtil.getImgDirFile();
        for(MultipartFile fileImg : files){

            // 拿到文件名
            String extName = fileImg.getOriginalFilename().substring(fileImg.getOriginalFilename().lastIndexOf("."));
            String uuidName = UUID.randomUUID().toString();

            try {
                // 构建真实的文件路径
                File newFile = new File(fileDir.getAbsolutePath() + File.separator +uuidName+ extName);

                // 上传图片到 -》 “绝对路径”
                fileImg.transferTo(newFile);

            } catch (IOException e) {
                e.printStackTrace();
            }
            student.setPhoto(uuidName+extName);
        }
        //保存学生信息到数据库
        try{
            int count = studentService.addStudent(student);
            if(count > 0){
                ajaxResult.setSuccess(true);
                ajaxResult.setMessage("保存成功");
            }else{
                ajaxResult.setSuccess(false);
                ajaxResult.setMessage("保存失败");
            }
        }catch (Exception e){
            e.printStackTrace();
            ajaxResult.setSuccess(false);
            ajaxResult.setMessage("保存失败");
        }

        ajaxResult.setSuccess(true);
        return ajaxResult;
    }

    /**
     * 修改学生信息
     * @param files
     * @param student
     * @return
     */
    @PostMapping("/editStudent")
    @ResponseBody
    public AjaxResult editStudent(@RequestParam("file") MultipartFile[] files,Student student){
        AjaxResult ajaxResult = new AjaxResult();

        // 存放上传图片的文件夹
        File fileDir = UploadUtil.getImgDirFile();
        for(MultipartFile fileImg : files){

            String name = fileImg.getOriginalFilename();
            if(name.equals("")){
                break;
            }

            // 拿到文件名
            String extName = fileImg.getOriginalFilename().substring(fileImg.getOriginalFilename().lastIndexOf("."));
            String uuidName = UUID.randomUUID().toString();

            try {
                // 构建真实的文件路径
                File newFile = new File(fileDir.getAbsolutePath() + File.separator +uuidName+ extName);
                // 上传图片到 -》 “绝对路径”
                fileImg.transferTo(newFile);

                Student byId = studentService.findById(student.getId());
                File file = new File(fileDir.getAbsolutePath() + File.separator + byId.getPhoto());
                if(file != null){
                    file.delete();
                }

            } catch (IOException e) {
                e.printStackTrace();
            }
            student.setPhoto(uuidName+extName);
        }

        try{
            int count = studentService.editStudent(student);
            if(count > 0){
                ajaxResult.setSuccess(true);
                ajaxResult.setMessage("修改成功");
            }else{
                ajaxResult.setSuccess(false);
                ajaxResult.setMessage("修改失败");
            }
        }catch(Exception e){
            e.printStackTrace();
            ajaxResult.setSuccess(false);
            ajaxResult.setMessage("修改失败");
        }
        return ajaxResult;
    }

    @GetMapping("/download/student_portrait/{filename}")
    public void downloadFile(@PathVariable("filename") String filename, HttpServletRequest request, HttpServletResponse response) throws IOException {
        // 获取图片文件的绝对路径，假设图片存储在"/path/to/images/"目录下
        String filePath = request.getServletContext().getRealPath("/upload/teacher_portrait/") + filename;

        // 检查文件是否存在
        File file = new File(filePath);
        if (!file.exists()) {
            response.sendError(HttpServletResponse.SC_NOT_FOUND);
            return;
        }

        // 设置响应内容类型为图片
        response.setContentType(Files.probeContentType(file.toPath()));

        // 设置响应头，指定文件名以及内容的长度
        response.setHeader("Content-Disposition", "attachment; filename=\"" + filename + "\"");
        response.setContentLength((int) file.length());

        // 将文件内容复制到响应输出流中
        try (InputStream inputStream = new FileInputStream(file)) {
            FileCopyUtils.copy(inputStream, response.getOutputStream());
        }
    }
}
