package ru.kai.assistantschedule.core.xml;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.Writer;
import org.w3c.dom.Document;
import org.w3c.dom.DocumentType;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

public class DOMSerializer {
	private String indent;
	private String lineSeparator;

	public DOMSerializer() {
		indent = " ";
		lineSeparator = "\n";
	}
	
	public void setLineSeparator(String lineSeparator) {
		this.lineSeparator = lineSeparator;
	}
	
	public void serialize(Document doc, OutputStream out) throws IOException {
		Writer writer = new OutputStreamWriter(out);
		serialize(doc, writer);
	}
	public void serialize(Document doc, File file) throws IOException {
		Writer writer = new FileWriter(file);
		serialize(doc, writer);
	}
	
	public void serialize(Document doc, Writer writer) throws IOException {
		// Start serialization recursion with no indenting
		serializeNode(doc, writer, "");
		writer.flush();
	}
	public void serializeNode(Node node, Writer writer, String indentLevel) throws IOException {
		// Determine action based on node type
		switch (node.getNodeType()) {
		case Node.DOCUMENT_NODE: writer.write("<?xml version='1.0' encoding='windows-1251'?>");
        writer.write(lineSeparator);
        // recurse on each child
        NodeList nodes = node.getChildNodes();
        if (nodes != null)
        	for (int i = 0; i < nodes.getLength(); i++)
        		serializeNode(nodes.item(i), writer, "");
        /*
         *  Document doc = (Document)node;
         *  serializeNode(doc.getDocumentElement( ), writer, " ");
         */
        break;
        case Node.ELEMENT_NODE: String name = node.getNodeName();
        writer.write(indentLevel + "<" + name);
        NamedNodeMap attributes = node.getAttributes();
        for (int i = 0; i < attributes.getLength(); i++) {
        	Node current = attributes.item(i);
        	writer.write(" " + current.getNodeName() + "=\"" + current.getNodeValue() + "\"");
        }
        writer.write(">");
        // recurse on each child
        NodeList children = node.getChildNodes();
        if (children != null) {
        	if ((children.item(0) != null) && (children.item(0).getNodeType() == Node.ELEMENT_NODE))
        		writer.write(lineSeparator);
        	for (int i = 0; i < children.getLength(); i++)
        		serializeNode(children.item(i), writer, indentLevel + indent);
        	if ((children.item(0) != null) && (children.item(children.getLength() - 1).getNodeType() == Node.ELEMENT_NODE))
        		writer.write(indentLevel);
        }
        writer.write("</" + name + ">");
        writer.write(lineSeparator);
        break;
        case Node.TEXT_NODE: writer.write(node.getNodeValue()); break;
        case Node.CDATA_SECTION_NODE: writer.write("<![CDATA[" + node.getNodeValue() + "]]>"); break;
        case Node.COMMENT_NODE: writer.write(indentLevel + "<!-- " + node.getNodeValue() + " -->"); writer.write(lineSeparator); break;
        case Node.PROCESSING_INSTRUCTION_NODE: writer.write("<?" + node.getNodeName() + " " + node.getNodeValue() + "?>"); writer.write(lineSeparator); break;
        case Node.ENTITY_REFERENCE_NODE: writer.write("&" + node.getNodeName() + ";"); break;
        case Node.DOCUMENT_TYPE_NODE: DocumentType docType = (DocumentType) node; writer.write("<!DOCTYPE " + docType.getName());
        if (docType.getPublicId() != null)
        	System.out.print(" PUBLIC \"" + docType.getPublicId() + "\" ");
        else writer.write(" SYSTEM ");
        writer.write("\"" + docType.getSystemId() + "\">");
        writer.write(lineSeparator);
        break;
		}
	}
}
