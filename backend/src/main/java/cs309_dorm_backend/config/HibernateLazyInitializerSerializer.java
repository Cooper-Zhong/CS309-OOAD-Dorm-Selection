package cs309_dorm_backend.config;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import org.hibernate.proxy.HibernateProxy;
import java.io.IOException;

public class HibernateLazyInitializerSerializer extends JsonSerializer<HibernateProxy> {
    @Override
    public void serialize(HibernateProxy value, JsonGenerator gen, SerializerProvider serializers) throws IOException {
        // Serialize as null 将 Hibernate 代理对象序列化为 null 值。
        gen.writeNull();
    }
}
