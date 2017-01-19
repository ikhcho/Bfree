package com.Bfree.admin;

import java.util.List;
import javax.annotation.Resource;
import org.springframework.stereotype.Service;

@Service("adminService")
public class AdminService {
	@Resource(name="adminDao")
	AdminDao dao;

	public int SaveAdmin(AdminVo vo) {
		return dao.SaveAdmin(vo);
	}
}
