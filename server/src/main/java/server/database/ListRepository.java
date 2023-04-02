package server.database;

import commons.CardList;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface ListRepository extends JpaRepository<CardList, UUID> {}
