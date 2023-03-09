package server.database;

import commons.CardList;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ListRepository extends JpaRepository<CardList, Integer> {}
