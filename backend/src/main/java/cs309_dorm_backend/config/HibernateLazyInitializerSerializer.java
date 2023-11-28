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
        // 由于 Hibernate 代理对象的属性值是延迟加载的，因此在序列化时会抛出异常。为了避免这种情况，我们将 Hibernate 代理对象序列化为 null 值。
        gen.writeNull();
    }
}
