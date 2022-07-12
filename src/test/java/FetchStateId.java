import io.restassured.http.ContentType;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import org.testng.Assert;
import org.testng.annotations.Test;
import static io.restassured.RestAssured.given;
public class FetchStateId {
    public JsonPath obj1,obj2;
    public String stateName;
    public static int stateId;
    public int districtId;
    public static String districtName;
    @Test(priority = 1)
    public void getStateId()
    {
              Response res= given()
                .contentType(ContentType.JSON)
                .get("https://cdn-api.co-vin.in/api/v2/admin/location/states");
              String response=res.asString();
        Assert.assertEquals(res.statusCode(),200);
        obj1=new JsonPath(response);
        for(int i=0;i<37;i++)
        {
            stateName=obj1.get("states["+i+"].state_name");
            if (stateName.equalsIgnoreCase("Karnataka"))
            {
                stateId=obj1.getInt("states["+i+"].state_id");
                break;
            }
        }
        System.out.println("StateName: "+stateName);
        System.out.println("StateId: "+stateId);
    }

    @Test(priority = 2)
    public void getDistrictId()
    {
        Response res1= given()
                .contentType(ContentType.JSON)
                .get("https://cdn-api.co-vin.in/api/v2/admin/location/districts/"+stateId);
        String response1=res1.asString();
        Assert.assertEquals(res1.statusCode(),200);
       obj2=new JsonPath(response1);
        for(int j=0;j<31;j++)
        {
            districtName=obj2.get("districts["+j+"].district_name");
            if (districtName.equalsIgnoreCase("Bangalore Urban"))
            {
                districtId=obj2.getInt("districts["+j+"].district_id");
                break;
            }
        }
        System.out.println("DistrictName: "+districtName);
        System.out.println("DistrictId: "+districtId);
    }
}
