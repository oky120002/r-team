/**
 * 
 */
package com.r.component.menu.context.xml;

import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;

import com.thoughtworks.xstream.converters.MarshallingContext;
import com.thoughtworks.xstream.converters.UnmarshallingContext;
import com.thoughtworks.xstream.converters.collections.AbstractCollectionConverter;
import com.thoughtworks.xstream.io.ExtendedHierarchicalStreamWriterHelper;
import com.thoughtworks.xstream.io.HierarchicalStreamReader;
import com.thoughtworks.xstream.io.HierarchicalStreamWriter;
import com.thoughtworks.xstream.mapper.Mapper;

/**
 * @author oky
 * 
 */
@SuppressWarnings("rawtypes")
public class MapCustomConverter extends AbstractCollectionConverter {

	/**
	 * @param mapper
	 */
	public MapCustomConverter(Mapper mapper) {
		super(mapper);
	}

	public boolean canConvert(Class type) {
		return Map.class.isAssignableFrom(type);
	}

	public void marshal(Object source, HierarchicalStreamWriter writer, MarshallingContext context) {
		Map map = (Map) source;
		for (Iterator iterator = map.entrySet().iterator(); iterator.hasNext();) {
			Entry entry = (Entry) iterator.next();
			ExtendedHierarchicalStreamWriterHelper.startNode(writer, "property", Entry.class);

			writer.addAttribute("key", entry.getKey().toString());
			writer.addAttribute("value", entry.getValue().toString());
			writer.endNode();
		}
	}

	public Object unmarshal(HierarchicalStreamReader reader, UnmarshallingContext context) {
		Map map = (Map) createCollection(context.getRequiredType());
		populateMap(reader, context, map);
		return map;
	}

	@SuppressWarnings("unchecked")
	protected void populateMap(HierarchicalStreamReader reader, UnmarshallingContext context, Map map) {
		while (reader.hasMoreChildren()) {
			reader.moveDown();
			Object key = reader.getAttribute("key");
			Object value = reader.getAttribute("value");
			map.put(key, value);
			reader.moveUp();
		}
	}
}
