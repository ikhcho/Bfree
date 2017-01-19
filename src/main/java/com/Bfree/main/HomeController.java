package com.Bfree.main;


import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.Bfree.machine.Device;
import com.Bfree.machine.MachineService;
import com.Bfree.machine.MachineVo;
import com.Bfree.admin.AdminService;
import com.Bfree.admin.AdminVo;
import com.Bfree.spark.Spark;

/**
 * Handles requests for the application home page.
 */
@Controller
public class HomeController {
	@Autowired
	@Qualifier("machineService")
	MachineService mService;
	
	@Autowired
	@Qualifier("adminService")
	AdminService aService;
	
	/**
	 * Simply selects the home view to render by returning its name.
	 */
	@RequestMapping(value = "/", method = RequestMethod.GET)
	public String home() {
		
		return "home";
	}
	
	@RequestMapping(value = "/admin_register")
	public String admin_register()
	{
		return "admin_register";
	}
	
	@RequestMapping(value = "/admin_register", method = RequestMethod.POST)
	public String admin_register(AdminVo newAV)
	{
		aService.SaveAdmin(newAV);
		Spark newRoom = new Spark();
		newRoom.create_room(newAV.getEmail());
		return "home";
	}
	
	@RequestMapping(value = "machine_register")
	public String machine_register()
	{
		return "machine_register";
	}
	
	@RequestMapping(value = "/machine_register", method = RequestMethod.POST)
	public String machine_register(MachineVo newMV)
	{
		Spark newMachine = new Spark();
		String msg = ""+newMV.getName() + " is being registered";
		Device newSet = new Device();
		
		mService.SaveMachine(newMV); // Insert a new machine data to DB
		newMachine.message(newMV.getEmail(), msg); // Create new message
		newSet.setThreshold(newMV.getName(), newMV.getIp(), newMV.getThreshold());// Set threshold
		
		return "home";
	}
	
	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public String list(@RequestParam(value = "email") String email,@RequestParam(value = "roomId") String roomId){
		Device mList = new Device();
		
		String list;
		
		List<MachineVo> LMV = mService.selectAllMachine(email);
		list = mList.sortList(LMV);
		
		mList.list(list,roomId);
		return "home";
	}
	
	@RequestMapping(value = "/info", method = RequestMethod.GET)
	public String info(@RequestParam(value = "name") String name,@RequestParam(value = "roomId") String roomId){
		
		Device mState = new Device();
		
		mState.state(name, roomId);
		return "info";
	}
	
	@RequestMapping(value = "/alarm", method = RequestMethod.GET)
	public String alarm(@RequestParam(value = "ip") String ip,@RequestParam(value = "data") String data){
		Spark alarm = new Spark();
		String email = mService.SearchEmail(ip).getEmail();
		String text = "Warning! Warning!" + mService.SearchEmail(ip).getName() + "(" + mService.SearchEmail(ip).getIp() +") current value : " + data;
		System.out.println(email+text);
		alarm.message(email, text);
		return "alarm";
	}
}
