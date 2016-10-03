package com.family.web.controller;

import java.text.SimpleDateFormat;
import java.util.Date;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.NumberUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.family.security.SecurityUtil;
import com.family.security.MyUserDetailsImpl;
import com.family.service.EventService;
import com.family.service.bean.EventBean;
import com.family.web.dto.EventDto;
import com.family.web.dto.StandardDto;

@Controller
public class EventController {
	private Logger  logger = Logger.getLogger("com.family.web.controller");
	
	@Autowired
	private EventService eventService;
	
	
	/**
	 * Store a New Event
	 * 
	 * @param startTimeStr : It is in the format of yyyy/MM/dd HH:mm:ss
	 * @param endTimeStr
	 * @param title
	 * @param description
	 * @param allDayStr
	 * @return
	 */
	@RequestMapping(value = "addNewEvent", method = RequestMethod.POST)		
	public @ResponseBody StandardDto addNewEvent(
			@RequestParam(value = "startTime", required = true) String startTimeStr,
			@RequestParam(value = "endTime", required = true) String endTimeStr,
			@RequestParam(value = "title", required = true) String title,
			@RequestParam(value = "description", required = true) String description,
			@RequestParam(value = "allDay", required = true) String allDayStr
			) {
		logger.info("addNewEvent!");	
		
		SimpleDateFormat dateTimeSdf = new SimpleDateFormat("yyyy/MM/dd HH:mm");
		
		StandardDto response = new StandardDto(false);
		try {
			System.out.println("startTimeStr = " + startTimeStr);
			System.out.println("endTimeStr = " + endTimeStr);
			System.out.println("title = " + title);
			System.out.println("description = " + description);
			System.out.println("allDayStr = " + allDayStr);
			
			MyUserDetailsImpl ud = SecurityUtil.getUserDetails();
			
			Date startTime = dateTimeSdf.parse(startTimeStr);
			Date endTime = dateTimeSdf.parse(endTimeStr);
			Boolean allDay = Boolean.valueOf(allDayStr);
			
			eventService.addNewEvent(ud.getUser(), startTime, endTime, title, description, allDay);

			response.setSuccess(true);
		} catch (Exception e) {
			response.setErrorMsg(e.getMessage());
			e.printStackTrace();
		}

		return response;
	}
	
	/**
	 * Update an Event
	 * 
	 * @param startTimeStr : It is in the format of yyyy/MM/dd HH:mm:ss
	 * @param endTimeStr
	 * @param title
	 * @param description
	 * @param allDayStr
	 * @return
	 */
	@RequestMapping(value = "updateEvent", method = RequestMethod.POST)		
	public @ResponseBody StandardDto updateEvent(
			@RequestParam(value = "eventId", required = true) String eventIdStr,
			@RequestParam(value = "startTime", required = true) String startTimeStr,
			@RequestParam(value = "endTime", required = true) String endTimeStr,
			@RequestParam(value = "title", required = true) String title,
			@RequestParam(value = "description", required = true) String description,
			@RequestParam(value = "allDay", required = true) String allDayStr
			) {
		logger.info("updateEvent!");	
		
		SimpleDateFormat dateTimeSdf = new SimpleDateFormat("yyyy/MM/dd HH:mm");
		
		StandardDto response = new StandardDto(false);
		try {
			System.out.println("eventIdStr = " + eventIdStr);
			System.out.println("startTimeStr = " + startTimeStr);
			System.out.println("endTimeStr = " + endTimeStr);
			System.out.println("title = " + title);
			System.out.println("description = " + description);
			System.out.println("allDayStr = " + allDayStr);
			
			Date startTime = dateTimeSdf.parse(startTimeStr);
			Date endTime = dateTimeSdf.parse(endTimeStr);
			Boolean allDay = Boolean.valueOf(allDayStr);
			
			eventService.updateEvent(SecurityUtil.getUserDetails().getUser(), NumberUtils.parseNumber(eventIdStr, Long.class),  startTime, endTime, title, description, allDay);

			response.setSuccess(true);
		} catch (Exception e) {
			response.setErrorMsg(e.getMessage());
			e.printStackTrace();
		}

		return response;
	}
	

	

	@RequestMapping(value = "deleteEvent.json", method = RequestMethod.POST)
	public @ResponseBody StandardDto deleteEvent(
			@RequestParam(value = "id", required = true) int id) {

		StandardDto response = new StandardDto(false);
		try {
			logger.info("id = " + id);
			eventService.deleteEvent(SecurityUtil.getUserDetails().getUser(), id);
			response.setSuccess(true);
		} catch (Exception e) {
			response.setErrorMsg(e.getMessage());
			e.printStackTrace();
		}
		return response;
	}


	@RequestMapping(value = "/getEvent", method = RequestMethod.GET)
	public @ResponseBody EventDto getEvent(String id) {

		EventBean event  = eventService.getEvent(Long.parseLong(id));
		EventDto dto = new EventDto(event);

		return dto;
	}

}
