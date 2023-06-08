package com.homepage.barter.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.homepage.barter.entity.Item;

public interface BarterRepository extends JpaRepository<Item, Long>{
	Item findItemById(Long itemId);
}
