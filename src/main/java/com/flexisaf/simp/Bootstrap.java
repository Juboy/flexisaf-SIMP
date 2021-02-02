package com.flexisaf.simp;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;

import com.flexisaf.simp.model.Department;
import com.flexisaf.simp.repository.DepartmentRepository;

@Component
public class Bootstrap implements ApplicationListener<ContextRefreshedEvent>{

	@Autowired
	private DepartmentRepository departmentRepository;
	
	@Override
	public void onApplicationEvent(ContextRefreshedEvent event) {
		if(!departmentRepository.findAll().isEmpty()) return;
		departmentRepository.deleteAll();
		
		
		Department department = new Department();
		department.setName("Mathematics");
		
		Department department1 = new Department();
		department1.setName("Chemistry");
		
		departmentRepository.save(department);
		departmentRepository.save(department1);
		
	}

}
