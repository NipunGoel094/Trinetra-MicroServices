package com.trinetra.controller;

import static com.trinetra.model.enums.Status.SUCCESS;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.trinetra.model.wrapper.ResponseWrapper;
import com.trinetra.service.AisLoginPacketService;

@RestController
@RequestMapping("/api/v1/logincheck")
public class AisLoginPacketController {

	@Autowired
	private AisLoginPacketService aisLoginPacketService;
	
	@GetMapping("/aisLoginCheck")
	public ResponseEntity<ResponseWrapper> getDevicHealthCheckeDetails(@RequestParam("page") int page, @RequestParam("size") int size )
	{	
		
		return new ResponseEntity<ResponseWrapper>(new ResponseWrapper(SUCCESS.name, aisLoginPacketService.getAllRecords(page, size)),
				HttpStatus.OK);
	}
	
	@GetMapping("/search")
	public ResponseEntity<ResponseWrapper> getSearchByColumns(@RequestParam("text") String text, @RequestParam("page") int page, @RequestParam("size") int size) {
		
		return new ResponseEntity<ResponseWrapper>(new ResponseWrapper(SUCCESS.name, aisLoginPacketService.searchByColumns(text,page,size)), HttpStatus.OK);
	}
	
	@GetMapping("/filterByCompany")
	public ResponseEntity<ResponseWrapper> getFilteredDataByCompany(@RequestParam(value = "date", required = false) String date, @RequestParam(value = "oemId", required = true) int oemId, @RequestParam("page") int page, @RequestParam("size") int size) {
		
		return new ResponseEntity<ResponseWrapper>(new ResponseWrapper(SUCCESS.name, aisLoginPacketService.filterDataByCompanyName(oemId, date, page, size)), HttpStatus.OK);
	}
	
}
