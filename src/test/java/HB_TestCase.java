import org.json.simple.parser.ParseException;
import org.testng.annotations.Test;
import java.io.IOException;


public class HB_TestCase extends AbstractTestCase {

    @Test()
    public void testCase() throws InterruptedException, IOException, ParseException {
        test.openUrl();
        test.registerAndLogin();
        test.fillCustomerInfo();
        test.selectProduct();
        test.continueShopping();
        test.addProductFromSearch();
        test.checkout();
        test.cart();
        test.shipping();
        test.payment();
    }
}
