package com.lww.serviceedu.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.lww.commonutils.R;
import com.lww.servicebase.exceptionhandler.GuliException;
import com.lww.serviceedu.entity.Teacher;
import com.lww.serviceedu.entity.vo.TeacherQuery;
import com.lww.serviceedu.service.TeacherService;
import org.apache.poi.ss.formula.functions.T;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * <p>
 * 讲师 前端控制器
 * </p>
 *
 * @author lww
 * @since 2020-05-23
 */
@RestController
@RequestMapping("eduservice/teacher")
@CrossOrigin
public class TeacherController {
    private static final Logger LOGGER = LoggerFactory.getLogger(TeacherController.class);
    @Autowired
    private TeacherService teacherService;
    @GetMapping("findAll")
    public R findAll(){
        List<Teacher> list = teacherService.list(null);
        return  R.ok().data("items",list);
    }

    @DeleteMapping("{id}")
    public R removeById(@PathVariable String id){
       boolean b =  teacherService.removeById(id);
       if(b){
           return R.ok();
       }else {
           return R.error();
       }
    }
    //分页查询讲师方法
    //current当前页
    //limit每页显示的记录数
    @GetMapping("pageTeacher/{current}/{limit}")
    public R pageListTeacher(@PathVariable long current,@PathVariable long limit){
        Page<Teacher> teacherPage = new Page<>(current,limit);
        teacherService.page(teacherPage, null);
        long total = teacherPage.getTotal();
        List<Teacher> records = teacherPage.getRecords();
        return R.ok().data("total", total).data("rows",records);
    }

    @PostMapping("pageTeacherCondition/{current}/{limit}")
    public R pageTeacherCondition(@PathVariable long current, @PathVariable  long limit,@RequestBody(required = false) TeacherQuery teacherQuery){
        Page<Teacher> teacherPage = new Page<>(current,limit);
        QueryWrapper<Teacher> wrapper = new QueryWrapper<>();
        String name = teacherQuery.getName();
        Integer level = teacherQuery.getLevel();
        String begin = teacherQuery.getBegin();
        String end = teacherQuery.getEnd();
        if(!StringUtils.isEmpty(name)){
            wrapper.like("name", name);
        }
        if(!StringUtils.isEmpty(level)){
            wrapper.eq("level",level);
        }
        if(!StringUtils.isEmpty(begin)){
            wrapper.ge("gmt_create",begin);
        }
        if(!StringUtils.isEmpty(end)){
            wrapper.le("gmt_create", end);
        }
        wrapper.orderByDesc("gmt_create");
        teacherService.page(teacherPage, wrapper);
        long total = teacherPage.getTotal();
        List<Teacher> records = teacherPage.getRecords();
        return R.ok().data("total", total).data("rows",records);
    }
    //添加讲师接口的方法
    @PostMapping("addTeacher")
    public R addTeacher(@RequestBody Teacher teacher){
        boolean save = teacherService.save(teacher);
        if(save){
            return R.ok();
        }else {
            return R.error();
        }
    }
    //根据讲师id进行查询
    @GetMapping("getTeacher/{id}")
    public R getTeacher(@PathVariable String id){
       try {
          // int i = 10/0;
       }catch (Exception e){
           throw new GuliException(20001,"执行了自定义异常处理");
       }
        Teacher teacher = teacherService.getById(id);
        return R.ok().data("teacher",teacher);
    }

    @PostMapping("updateTeacher/{Id}")
    public R updateTeacher(@PathVariable String Id,@RequestBody Teacher teacher){
        teacher.setId(Id);
        Teacher bean = new Teacher();
       BeanUtils.copyProperties(teacher,bean);
        LOGGER.info(bean.toString());
        boolean flag = teacherService.updateById(bean);

        if(flag){
            return R.ok();
        }else {
            return R.error();
        }
    }
}

