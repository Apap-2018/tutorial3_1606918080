package com.apap.tutorial3.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.apap.tutorial3.model.CarModel;
import com.apap.tutorial3.service.CarService;

@Controller
public class CarController {
	@Autowired
	private CarService mobilService;
	
	@RequestMapping("/car/add")
	public String add(@RequestParam(value = "id", required = true) String id, @RequestParam(value = "brand", required = true) String brand, @RequestParam(value = "type", required = true) String type, @RequestParam(value = "price", required = true) Long price, @RequestParam(value = "amount", required = true) Integer amount) {
		CarModel car = new CarModel(id, brand, type, price, amount);
		mobilService.addCar(car);
		return "add";
	}
	
	@RequestMapping("/car/view")
	public String view(@RequestParam("id") String id, Model model) {
		CarModel archive = mobilService.getCarDetail(id);
		
		model.addAttribute("car", archive);
		return "view-car";
	}
	
	@RequestMapping("/car/viewall")
	public String viewall(Model model) {
		List<CarModel> archive = mobilService.getCarList();
		
		model.addAttribute("listCar", archive);
		return "viewall-car";
	}
	
	@RequestMapping("/car/view/{id}")
	public String viewcar(@PathVariable String id, Model model) {
		String status = "";
		List<CarModel> archive = mobilService.getCarList();
		if(id.equals(null)) {
			status += "Kosong";
			model.addAttribute("hasil",status);
			return "error";
		}
		else {
			for(CarModel car: archive) {
				if(car.getId().equals(id)) {
					model.addAttribute("car",car);
					return "view-car";
				}
			}
			status += "Tidak Ditemukan";
			model.addAttribute("hasil",status);
			return "error";
		}
	}
	
	@RequestMapping("/car/update/{id}/amount/{amount}")
	public String updateamount(@PathVariable String id, @PathVariable String amount, Model model) {
		int jumlah = Integer.parseInt(amount);
		int angka = 0;
		String status = "";
		List<CarModel> archive = mobilService.getCarList();
		if(id.equals(null)) {
			status += "Kosong";
			model.addAttribute("hasil", status);
			return "error";
		}
		else {
			for(CarModel car: archive) {
				if(car.getId().equals(id)) {
					angka = car.getAmount();
					model.addAttribute("nilai",angka);
					car.setAmount(jumlah);
					model.addAttribute("car",car);
					return "update-amount";
				}
			}
			status += "Tidak Ditemukan";
			model.addAttribute("hasil",status);
			return "error";
		}
	}
	
	@RequestMapping("/car/delete/{id}")
	public String delete(@PathVariable String id, Model model) {
		String status = "";
		List<CarModel> archive = mobilService.getCarList();
		if(id.equals(null)) {
			status += "Kosong";
			model.addAttribute("hasil",status);
			return "error";
		}
		else {
			for(CarModel car: archive) {
				if(car.getId().equals(id)) {
					archive.remove(car);
					status += "Data sudah berhasil dihapus";
					model.addAttribute("pesan",status);
					return "delete";
				}
			}
			status += "Tidak Ditemukan";
			model.addAttribute("hasil",status);
			return "error";
		}
	}
}