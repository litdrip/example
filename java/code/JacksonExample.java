import org.codehaus.jackson.JsonEncoding;
import org.codehaus.jackson.JsonGenerator;
import org.codehaus.jackson.JsonParseException;
import org.codehaus.jackson.map.JsonMappingException;
import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.node.JsonNodeFactory;

import java.io.IOException;
import java.util.*;

/**Jackson 测试       has upload
 * Created by shaokun.zhi on 2015/12/21 0021.
 */
public class JacksonExample {
    private JsonGenerator jsonGenerator = null;
    private ObjectMapper objectMapper = null;
    private AccountBean bean = null;

    public static void main(String[] args){
        JacksonExample t = new JacksonExample();
        t.init();
        t.writeEntityJson();
        t.writeMapJson();
        t.destory();
    }

    public void init(){
        bean = new AccountBean();
        bean.setAddress("china.HangZhou");
        bean.setEmail("daka1@163.com");
        bean.setId(1);
        bean.setName("ly");
        objectMapper = new ObjectMapper();
        try{
            jsonGenerator = objectMapper.getJsonFactory().createJsonGenerator(System.out, JsonEncoding.UTF8);
        }catch (IOException e){
            e.printStackTrace();
        }
    }

    /** 对象 => JSON */
    public void writeEntityJson(){
        System.out.println("对象 => JSON");
        try {
            System.out.println("JsonGenerator");
            jsonGenerator.writeObject(bean);
            System.out.println("");
            System.out.println("OjbectMapper");
            System.out.println(objectMapper.writeValueAsString(bean));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /** Map => JSON */
    public void writeMapJson(){
        System.out.println("\nMap => JSON");
        try {
            Map<String,Object> map = new HashMap<String,Object>();
            map.put("name",bean.getName());
            map.put("account",bean);
            System.out.println("JsonGenerator");
            jsonGenerator.writeObject(map);
            System.out.println("");
            System.out.println("OjbectMapper");
            System.out.println(objectMapper.writeValueAsString(map));
            System.out.println("");
        }catch (IOException e){
            e.printStackTrace();
        }
    }

    /** List => JSON */
    public void writeListJSON() {
        try {
            List<AccountBean> list = new ArrayList<AccountBean>();
            list.add(bean);

            bean = new AccountBean();
            bean.setId(2);
            bean.setAddress("address2");
            bean.setEmail("email2");
            bean.setName("haha2");
            list.add(bean);

            System.out.println("jsonGenerator");
            //list转换成JSON字符串
            jsonGenerator.writeObject(list);
            System.out.println();
            System.out.println("ObjectMapper");
            //用objectMapper直接返回list转换成的JSON字符串
            System.out.println("1###" + objectMapper.writeValueAsString(list));
            System.out.print("2###");
            //objectMapper list转换成JSON字符串
            System.out.println(objectMapper.writeValueAsString(list));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void writeOthersJSON() {
        try {
            String[] arr = { "a", "b", "c" };
            System.out.println("jsonGenerator");
            String str = "hello world jackson!";
            //byte
            jsonGenerator.writeBinary(str.getBytes());
            //boolean
            jsonGenerator.writeBoolean(true);
            //null
            jsonGenerator.writeNull();
            //float
            jsonGenerator.writeNumber(2.2f);
            //char
            jsonGenerator.writeRaw("c");
            //String
            jsonGenerator.writeRaw(str, 5, 10);
            //String
            jsonGenerator.writeRawValue(str, 5, 5);
            //String
            jsonGenerator.writeString(str);
            jsonGenerator.writeTree(JsonNodeFactory.instance.POJONode(str));
            System.out.println();

            //Object
            jsonGenerator.writeStartObject();//{
            jsonGenerator.writeObjectFieldStart("user");//user:{
            jsonGenerator.writeStringField("name", "jackson");//name:jackson
            jsonGenerator.writeBooleanField("sex", true);//sex:true
            jsonGenerator.writeNumberField("age", 22);//age:22
            jsonGenerator.writeEndObject();//}

            jsonGenerator.writeArrayFieldStart("infos");//infos:[
            jsonGenerator.writeNumber(22);//22
            jsonGenerator.writeString("this is array");//this is array
            jsonGenerator.writeEndArray();//]

            jsonGenerator.writeEndObject();//}

            AccountBean bean = new AccountBean();
            bean.setAddress("address");
            bean.setEmail("email");
            bean.setId(1);
            bean.setName("haha");
            //complex Object
            jsonGenerator.writeStartObject();//{
            jsonGenerator.writeObjectField("user", bean);//user:{bean}
            jsonGenerator.writeObjectField("infos", arr);//infos:[array]
            jsonGenerator.writeEndObject();//}

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void readJson2Entity() {
        String json = "{\"address\":\"address\",\"name\":\"haha\",\"id\":1,\"email\":\"email\"}";
        try {
            AccountBean acc = objectMapper.readValue(json, AccountBean.class);
            System.out.println(acc.getName());
            System.out.println(acc);
        } catch (JsonParseException e) {
            e.printStackTrace();
        } catch (JsonMappingException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void readJson2List() {
        String json = "[{\"address\": \"address2\",\"name\":\"haha2\",\"id\":2,\"email\":\"email2\"},"+
                "{\"address\":\"address\",\"name\":\"haha\",\"id\":1,\"email\":\"email\"}]";
        try {
            List<LinkedHashMap<String, Object>> list = objectMapper.readValue(json, List.class);
            System.out.println(list.size());
            for (int i = 0; i < list.size(); i++) {
                Map<String, Object> map = list.get(i);
                Set<String> set = map.keySet();
                for (Iterator<String> it = set.iterator();it.hasNext();) {
                    String key = it.next();
                    System.out.println(key + ":" + map.get(key));
                }
            }
        } catch (JsonParseException e) {
            e.printStackTrace();
        } catch (JsonMappingException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void readJson2Array() {
        String json = "[{\"address\": \"address2\",\"name\":\"haha2\",\"id\":2,\"email\":\"email2\"},"+
                "{\"address\":\"address\",\"name\":\"haha\",\"id\":1,\"email\":\"email\"}]";
        try {
            AccountBean[] arr = objectMapper.readValue(json, AccountBean[].class);
            System.out.println(arr.length);
            for (int i = 0; i < arr.length; i++) {
                System.out.println(arr[i]);
            }

        } catch (JsonParseException e) {
            e.printStackTrace();
        } catch (JsonMappingException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void readJson2Map() {
        String json = "{\"success\":true,\"A\":{\"address\": \"address2\",\"name\":\"haha2\",\"id\":2,\"email\":\"email2\"},"+
                "\"B\":{\"address\":\"address\",\"name\":\"haha\",\"id\":1,\"email\":\"email\"}}";
        try {
            Map<String, Map<String, Object>> maps = objectMapper.readValue(json, Map.class);
            System.out.println(maps.size());
            Set<String> key = maps.keySet();
            Iterator<String> iter = key.iterator();
            while (iter.hasNext()) {
                String field = iter.next();
                System.out.println(field + ":" + maps.get(field));
            }
        } catch (JsonParseException e) {
            e.printStackTrace();
        } catch (JsonMappingException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void destory(){
        try{
            if(jsonGenerator != null){
                jsonGenerator.flush();
                if(!jsonGenerator.isClosed()){
                    jsonGenerator.close();
                }
            }
            jsonGenerator = null;
            objectMapper = null;
            bean = null;
            System.gc();
        }catch (IOException e){
            e.printStackTrace();
        }
    }

    //用户类
    private class AccountBean {
        private int id;
        private String name;
        private String email;
        private String address;
        private Birthday birthday;

        @Override
        public String toString() {
            return "AccountBean{" +
                    "id=" + id + ", name='" + name + '\'' +  ", email='" + email + '\'' +
                    ", address='" + address + '\'' + ", birthday=" + birthday + '}';
        }

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getEmail() {
            return email;
        }

        public void setEmail(String email) {
            this.email = email;
        }

        public String getAddress() {
            return address;
        }

        public void setAddress(String address) {
            this.address = address;
        }

        public Birthday getBirthday() {
            return birthday;
        }

        public void setBirthday(Birthday birthday) {
            this.birthday = birthday;
        }
    }

    //生日
    private class Birthday {
        private String birthday;

        public Birthday() {};

        public Birthday(String birthday){
            super();
            this.birthday=birthday;
        }

        public String getBirthday() {
            return birthday;
        }

        public void setBirthday(String birthday) {
            this.birthday = birthday;
        }
    }

}