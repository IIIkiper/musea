package com.musea.model;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import com.jolbox.bonecp.BoneCPDataSource;

@Configuration
public class DataCfg {
	
	@Value("${jdbc.driver}") 
	private String driver;
	@Value("${jdbc.url}")
	private String url;
	@Value("${db.username}") 
	private String username;
	@Value("${db.password}") 
	private String password;
	@Value("${pool.maxConnectionsPerPartition}") 
	private int maxConnectionsPerPartition;
	@Value("${pool.minConnectionsPerPartition}") 
	private int minConnectionsPerPartition;
	@Value("${pool.partitionCount}") 
	private int partitionCount;
	@Value("${pool.acquireConnectionsPerPartition}") 
	private int acquireConnectionsPerPartition;
	@Value("${pool.releaseThreads}") 
	private int releaseThreads;
	
	@Bean(destroyMethod = "close")
	public DataSource dataSource() {
		BoneCPDataSource bcds = new BoneCPDataSource();
		bcds.setDriverClass(driver);
		bcds.setJdbcUrl(url);
		bcds.setUsername(username);
		bcds.setPassword(password);
		bcds.setMaxConnectionsPerPartition(maxConnectionsPerPartition);
		bcds.setMinConnectionsPerPartition(minConnectionsPerPartition);
		bcds.setPartitionCount(partitionCount);
		bcds.setAcquireIncrement(acquireConnectionsPerPartition);
		bcds.setReleaseHelperThreads(releaseThreads);		
		return bcds;
	}
}