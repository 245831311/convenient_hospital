package com.hospital.util;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;

import org.apache.log4j.Logger;

import com.esotericsoftware.kryo.Kryo;
import com.esotericsoftware.kryo.io.Input;
import com.esotericsoftware.kryo.io.Output;

/**
 * 序列化工具类
 * 
 * @author yfb
 *
 */
public class SerializableUtils {
	private static Logger logger = Logger.getLogger(SerializableUtils.class);
    private static Kryo kryo;

    static {
        kryo = new Kryo();
        kryo.setReferences(false);
    }

	public static <T> void register(Class<T> t) {
		kryo.register(t);
	}

    public static <T> byte[] serialize(T relation) {
        byte[] bytes = null;
        ByteArrayOutputStream baos = null;
        Output output = null;
        try {
            baos = new ByteArrayOutputStream();
            output = new Output(baos);
            kryo.writeObjectOrNull(output, relation, relation.getClass());
            output.flush();
            bytes = baos.toByteArray();
        } catch (Exception e) {
        	logger.error("serialize exception!",e);
        } finally {
            try {
                if (output != null) {
                    output.close();
                }
                if (baos != null) {
                    baos.close();
                }
            } catch (Exception e) {
            }
        }

        return bytes;
    }

    public static <T> T unserialize(byte[] bytes, Class<T> t) {
        T relation = null;
        Input input = null;
        ByteArrayInputStream bais = null;
        try {
        	bais = new ByteArrayInputStream(bytes);
            input = new Input(bais);
            relation = (T) kryo.readObjectOrNull(input, t);
        } catch (Exception e) {
        	logger.error("unserialize exception!",e);
        } finally {
            if (input != null) {
                input.close();
            }
        }
        return relation;
    }

}
