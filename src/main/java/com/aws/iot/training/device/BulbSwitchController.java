package com.aws.iot.training.device;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.amazonaws.services.iot.client.AWSIotException;
import com.amazonaws.services.iot.client.AWSIotTimeoutException;
import com.aws.iot.training.device.ShadowThing.Document;
@Controller
@RequestMapping("/bulbcontroller")
public class BulbSwitchController {

	@Autowired
	private DeviceService  deviceService;	
	
	
	@GetMapping("")
    public String index(@RequestParam(name = "name", required = false, defaultValue = "World") String name,
			HttpServletRequest request, Model model) {
        return "index";
    }
	@RequestMapping(value = "/updateDesiredValueOfBulb", produces=MediaType.APPLICATION_JSON_VALUE, method = RequestMethod.GET)
    @ResponseBody
    public String blueBulbStatus(@RequestParam("bulb") String bulbType,
    		@RequestParam("status") boolean status,
    		HttpServletRequest request, Model model) {
		
		try {
			deviceService.updateDeviceDesiredStatus(BulbType.valueOf(BulbType.class, bulbType.toUpperCase()), status);
		} catch (InterruptedException | AWSIotException | AWSIotTimeoutException | IOException e) {
			System.out.println("Error Occured While Sending Message");
		}
		return "index";
    }
	
	@RequestMapping(value = "/getLastStatus", produces=MediaType.APPLICATION_JSON_VALUE, method = RequestMethod.GET)
    @ResponseBody
    public Document getLastStatus(HttpServletRequest request) {
		Document document = null;
		try {
			document =  deviceService.getDeviceLastStatus();
		} catch (InterruptedException | AWSIotException | IOException | AWSIotTimeoutException e) {
			System.out.println("Not able to capture device last status.");
			e.printStackTrace();
		}
        return document;
    }
	
}
