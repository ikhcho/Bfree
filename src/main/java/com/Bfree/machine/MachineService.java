package com.Bfree.machine;

import java.util.List;
import javax.annotation.Resource;
import org.springframework.stereotype.Service;

@Service("machineService")
public class MachineService {
	@Resource(name="machineDao")
	MachineDao dao;

	public int SaveMachine(MachineVo vo) {
		return dao.SaveMachine(vo);
	}
	
	public MachineVo SearchEmail(String ip){
		return dao.SearchEmail(ip);
	}
	public List<MachineVo> selectAllMachine(String email) {
		return dao.selectAllMachine(email);
	}
}
