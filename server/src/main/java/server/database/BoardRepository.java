package server.database;

import commons.Board;
import commons.Card;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BoardRepository extends JpaRepository<Board, Integer> {}
