package com.devsuperior.bds04.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.devsuperior.bds04.dto.EventDTO;
import com.devsuperior.bds04.entities.City;
import com.devsuperior.bds04.entities.Event;
import com.devsuperior.bds04.repositorys.EventRepository;

@Service
public class EventService {
	
	@Autowired
	private EventRepository repository;		

	@Transactional(readOnly = true)
	public Page<EventDTO> findAllPaged(Pageable pageable){
		Page<Event> list = repository.findAll(pageable);		
		return list.map(x -> new EventDTO(x));  
	}
	
	@Transactional
	public EventDTO insert(EventDTO dto) {
		Event newEvent = new Event();
		copyDtoToEntity(dto, newEvent);
		newEvent = repository.save(newEvent);
		return new EventDTO(newEvent);
	}
	
	private void copyDtoToEntity(EventDTO dto, Event entity) {
		entity.setName(dto.getName()); 
		entity.setDate(dto.getDate()); 
		entity.setUrl(dto.getUrl());
		entity.setCity(new City(dto.getCityId(), null));
	}
}
