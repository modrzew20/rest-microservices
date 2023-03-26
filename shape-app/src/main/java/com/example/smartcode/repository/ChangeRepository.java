package com.example.smartcode.repository;

import com.example.smartcode.entity.Change;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface ChangeRepository extends JpaRepository<Change, UUID> {


}
