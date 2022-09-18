package pablosz.app.Implement;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Component;

import pablosz.app.sesion;
@Component
public interface funcionesSQL extends JpaRepository<sesion,Integer>
{

}
