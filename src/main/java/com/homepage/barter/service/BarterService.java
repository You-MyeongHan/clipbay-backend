package com.homepage.barter.service;

import org.springframework.stereotype.Service;

import com.homepage.barter.entity.Item;
import com.homepage.barter.repository.BarterRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class BarterService {
	private final BarterRepository barterRepository;
	
	public Item getItemById(Long itemId) {
		Item item=barterRepository.findItemById(itemId);
		return item;
	}
}