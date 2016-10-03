package com.family.web.controller;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.family.enums.SectionEnum;
import com.family.security.SecurityUtil;
import com.family.service.WallService;
import com.family.service.bean.UserBean;
import com.family.web.dto.StandardDto;

@Controller
public class WallController {

	@Autowired
	private WallService wallService;
	
	private Logger  logger = Logger.getLogger("com.family.web.controller");

	@RequestMapping(value = "deleteWallMessage.json", method = RequestMethod.POST)
	public  @ResponseBody StandardDto deleteWallMessage(
			@RequestParam(value = "id", required = true) String id) {
		StandardDto response = new StandardDto(false);

		try {
			logger.debug("deleteWallMessage> id = " + id);
			wallService.deleteWallMessage(Long.parseLong(id));
			response.setSuccess(true);
		} catch (Exception e) {
			response.setErrorMsg(e.getMessage());
			e.printStackTrace();
		}
		return response;
	}

	@RequestMapping(value = "addMyMessage.json", method = RequestMethod.POST)
	public  @ResponseBody StandardDto addMyMessage(
			@RequestParam(value = "wallMessageText", required = true) String wallMessageText) {
		StandardDto response = new StandardDto(false);

		try {
			UserBean user = SecurityUtil.getUserDetails().getUser();
			String myWallMessage = user.getFirstName() + " said : " + wallMessageText;
			
			logger.debug("addMyMessage> myWallMessage = " + myWallMessage);
			
			wallService.addWallMessage(user, SectionEnum.Home, myWallMessage);
			response.setSuccess(true);
		} catch (Exception e) {
			response.setErrorMsg(e.getMessage());
			e.printStackTrace();
		}
		return response;
	}

	
}
