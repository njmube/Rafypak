package com.tikal.boveda.dao;


import java.util.List;



import com.tikal.boveda.modelo.DatosXml;

public interface DatosXmlDao {
	
	public void save(DatosXml d);

	public void delete(DatosXml d);

	public void update(DatosXml d);
	
	public DatosXml consult(Long id);
	
	public List<DatosXml> getByProveedor(String rfc);
	
	public List<DatosXml> getByUuid(String uuid);
		
	public List<DatosXml> findAll();	


}