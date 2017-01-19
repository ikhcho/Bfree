package com.Bfree.machine;

import java.util.List;

import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;

@Repository("machineDao")
public class MachineDao {

	@Autowired
	@Qualifier("sqlSessionTemplate")
	private SqlSessionTemplate template;
	
	public int SaveMachine(MachineVo vo) {
		return template.insert("bfree.SaveMachine",vo);
	}
	
	public MachineVo SearchEmail(String ip){
		return template.selectOne("bfree.SearchEmail",ip);
	}
	
	public List<MachineVo> selectAllMachine(String email) {
		// TODO Auto-generated method stub
		return template.selectList("bfree.selectAllMachine",email);
	}
}
