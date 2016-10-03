package com.family.web.controller;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.family.security.SecurityUtil;
import com.family.security.MyUserDetailsImpl;
import com.family.service.AccountService;
import com.family.service.WallService;
import com.family.service.bean.FamilyBean;
import com.family.service.bean.WallMessageBean;
import com.family.web.dto.WallMessageDto;

/*
 * Home Page Controller
 */
@Controller
public class HomeController {
	
	@Autowired
	private WallService wallService;
	
	@Autowired
	private AccountService accountService;
	
	@RequestMapping(value="/home", method=RequestMethod.GET)	
	public ModelAndView showHome() {
		MyUserDetailsImpl ud = SecurityUtil.getUserDetails();		
		
		ModelAndView mav = new ModelAndView("home.view");
		FamilyBean family = accountService.findFamily(Long.valueOf(ud.getUser().getFamilyId()));
		mav.addObject("user", ud.getUser());
		mav.addObject("family", family);
	
		List<WallMessageBean> wallMsgEntityList = wallService.findFamilyWallMessages(ud.getUser().getFamilyId());
		
		List<WallMessageDto> wallMessages = new ArrayList<WallMessageDto>();
		
		if (wallMsgEntityList.size() > 0) {
			for (WallMessageBean wmEntity : wallMsgEntityList) {
				wallMessages.add(new WallMessageDto(wmEntity));
			}
			
		}
		Collections.sort(wallMessages);
		
		mav.addObject("wallMessages", wallMessages);
		
		return mav;		
	}

}
