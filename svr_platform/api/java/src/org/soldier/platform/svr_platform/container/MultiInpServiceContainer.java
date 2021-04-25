package org.soldier.platform.svr_platform.container;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.soldier.platform.svr_platform.client.RAWithInpServiceFinder;
import org.soldier.platform.svr_platform.client.ServiceFinderFactory;
import org.soldier.platform.svr_platform.comm.SvrConfiguration;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

public abstract class MultiInpServiceContainer {
	
	protected MultiInpServiceContainer() {
		SvrConfiguration.setIsUsingInpService(true);
	}
	
	public static interface Callback {
		public void InpServiceCreated(InpService service);
	}
	
	public void loadServices(List<InpService> serviceList) {
		doLoadServices(serviceList);
		SvrConfiguration.setInpServiceList(serviceList);
		ServiceFinderFactory.setServiceFinder(new RAWithInpServiceFinder());
	}
	
	public abstract void destory();
	
	protected abstract void doLoadServices(List<InpService> serviceList);
	
	public void loadServiceFromXml(String xmlFilePath, Callback callback) 
			throws ParserConfigurationException, SAXException, IOException {
		DocumentBuilder db = DocumentBuilderFactory.newInstance().newDocumentBuilder();
		Document doc = db.parse(xmlFilePath);
		NodeList serviceList = doc.getElementsByTagName("service");
		
		List<InpService> inpServiceResults = new ArrayList<InpService>();
		for (int si = 0; si < serviceList.getLength(); ++si) {
			Node service = serviceList.item(si);
			InpService inpService = new InpService();
			
			NodeList serviceDescNodeList = service.getChildNodes();
			for (int ni = 0; ni < serviceDescNodeList.getLength(); ++ni) {
				Node serviceDescNode = serviceDescNodeList.item(ni);
				if (serviceDescNode.getNodeName().equals("service_name")) {
					inpService.setServiceName(serviceDescNode.getTextContent().trim());
				} else if (serviceDescNode.getNodeName().equals("service_key")) {
					inpService.setServiceKey(Integer.valueOf(serviceDescNode.getTextContent().trim()));
				} else if (serviceDescNode.getNodeName().equals("service_class")) {
					inpService.setServiceClass(serviceDescNode.getTextContent().trim());
				} else if (serviceDescNode.getNodeName().equals("properties")) {
					Properties props = new Properties();
					
					NodeList servicePropsList = serviceDescNode.getChildNodes();
					for (int pi = 0; pi < servicePropsList.getLength(); ++pi) {
						Node propNode = servicePropsList.item(pi);
						if (propNode.getNodeName().equals("property")) {
							String name = null;
							String value = null;
							Node nameNode = propNode.getAttributes().getNamedItem("name");
							if (nameNode != null) {
								name = nameNode.getNodeValue().trim();
							}
							Node valueNode = propNode.getAttributes().getNamedItem("value");
							if (valueNode != null) {
								value = valueNode.getNodeValue().trim();
							}
							props.put(name, value);
						}
					}
					
					inpService.setServiceProperties(props);
				}
			}
			if (callback != null) {
				callback.InpServiceCreated(inpService);
			}
			inpServiceResults.add(inpService);
		}
		
		loadServices(inpServiceResults);
	}
}
