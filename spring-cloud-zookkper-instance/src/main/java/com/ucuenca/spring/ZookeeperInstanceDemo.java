package com.ucuenca.spring;

import java.util.HashMap;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.zookeeper.discovery.ZookeeperInstance;
import org.springframework.cloud.zookeeper.serviceregistry.ServiceInstanceRegistration;
import org.springframework.cloud.zookeeper.serviceregistry.ServiceInstanceRegistration.RegistrationBuilder;
import org.springframework.cloud.zookeeper.serviceregistry.ZookeeperRegistration;
import org.springframework.cloud.zookeeper.serviceregistry.ZookeeperServiceRegistry;

public class ZookeeperInstanceDemo {

	@Autowired(required = false)
	protected ZookeeperServiceRegistry serviceRegistry;

	public ZookeeperInstanceDemo() {
		registerInZookeeper("instanceId", "partitionId");
	}
	private void registerInZookeeper(String instanceId, String partitionId) {

		HashMap<String, String> metadata = new HashMap<>();
		if (partitionId != null) {
			metadata.put("PARTITION_ID_KEY", partitionId);
		}

		ZookeeperInstance zookeeperInstance = new ZookeeperInstance(instanceId, "serviceId", metadata);
		String address = "";
		RegistrationBuilder builder = ServiceInstanceRegistration.builder().id(instanceId).address(address).port(2181)
				.name("serviceId").payload(zookeeperInstance).uriSpec("{scheme}://{address}:{port}");

		ZookeeperRegistration registration = builder.build();

		serviceRegistry.register(registration);
	}
}
