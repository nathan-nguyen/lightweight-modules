import net.sf.saxon.s9api.*;
import javax.xml.transform.stream.StreamSource;
import java.io.File;

public class XMLParser {
	public static void main(String[] args){
		if (args.length != 3) {
			System.out.println("Syntax Error: java -cp \"saxon9ee.jar;.\" XMLParser <XSLFile> <XMLFile> <OutFile>");
			return;
		}

		String xslFileName = args[0];
		String xmlFileName = args[1];
		String outputFileName = args[2];

		Processor processor = new Processor(true);
		XsltCompiler xsltCompiler = processor.newXsltCompiler();

		try {
			XsltExecutable xsltExecutable = xsltCompiler.compile(new StreamSource(new File(xslFileName)));
			XdmNode xdmNode = processor.newDocumentBuilder().build(new StreamSource(new File(xmlFileName)));
			Serializer serializer = processor.newSerializer(new File(outputFileName));

			XsltTransformer xsltTransformer = xsltExecutable.load();
			xsltTransformer.setInitialContextNode(xdmNode);
			xsltTransformer.setDestination(serializer);
			xsltTransformer.transform();
		} catch (SaxonApiException e){
			e.printStackTrace();
		}
	}
}
