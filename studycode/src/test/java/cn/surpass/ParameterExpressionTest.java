package cn.surpass;

import org.apache.ibatis.builder.ParameterExpression;
import org.junit.Test;

import java.util.Map;

/**
 * ssmstudy
 * cn.surpass
 *
 * @author surpass
 * @date 2020/1/4
 */
public class ParameterExpressionTest {

    @Test
    public void test1(){
        String expression1 = "/a.b/:c,d=e,f=g";
        ParameterExpression p = new ParameterExpression(expression1);
        for (Map.Entry kv:p.entrySet()) {
            System.out.println(kv.getKey() + "=>" + kv.getValue());
        }
    }
}
