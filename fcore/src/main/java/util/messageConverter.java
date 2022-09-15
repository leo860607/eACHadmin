package util;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;

import org.apache.commons.io.output.StringBuilderWriter;


public class messageConverter {
	
	public static String marshalling(Object obj) throws JAXBException{
		JAXBContext context = JAXBContext.newInstance(socketPackage.class);
        Marshaller m = context.createMarshaller();
        m.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
        
        StringBuilderWriter sbw = new StringBuilderWriter();
        m.marshal(obj, sbw);
        return sbw.toString();
	}
	
	public static socketPackage unmarshalling(String xml) throws JAXBException{
		JAXBContext jc = JAXBContext.newInstance(socketPackage.class);
        Unmarshaller u = jc.createUnmarshaller();
        
        InputStream is = new ByteArrayInputStream(xml.getBytes(StandardCharsets.UTF_8));
        socketPackage msg = (socketPackage) u.unmarshal(is);

        return msg;
	}
}
