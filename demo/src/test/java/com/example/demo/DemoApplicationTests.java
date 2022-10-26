package com.example.demo;

import io.appium.java_client.TouchAction;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.touch.offset.PointOption;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.springframework.boot.test.context.SpringBootTest;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.concurrent.TimeUnit;
@Slf4j
@SpringBootTest
public class DemoApplicationTests {
    public AndroidDriver driver;
    /**
     * BOSS自动海投
     * @throws MalformedURLException
     */
    @SneakyThrows
    @Test
    public void haitou() throws MalformedURLException{
        //创建一个配置对象，保存这6个配置
        DesiredCapabilities capabilities = new DesiredCapabilities();
        capabilities.setCapability("noReset","true");
        capabilities.setCapability("fullReset","false");
        //1、指定platformName--平台名
        capabilities.setCapability("platformName","Android");
        //2、指定deviceName-设备名
        capabilities.setCapability("deviceName","DWT7N18530002278");
        //3、指定appPackage --测试App标识
        capabilities.setCapability("appPackage","com.huawei.android.launcher");
        //4、指定appActivity --启动App的
        capabilities.setCapability("appActivity","com.huawei.android.launcher.unihome.UniHomeLauncher");
        //让配置生效--要和Appium Server建立通讯连接，把这些配置传递给Appium服务  --驱动
        //两个参数：第一个参数：Appium通讯地址（包括IP地址+端口号） 第二个参数：配置对象
        //---/wd/hub不要管为什么，加上就对了
        URL url = new URL("http://127.0.0.1:4723/wd/hub");
        //初始化动作，打开测试App
        driver = new AndroidDriver(url,capabilities);

        //设置touch动作用来模拟拖动屏幕
        TouchAction action = new TouchAction(driver);

        //隐式等待
        driver.manage().timeouts().implicitlyWait(8, TimeUnit.SECONDS);

        driver.findElement(By.xpath("//android.widget.TextView[@content-desc='BOSS直聘']")).click();//从主界面进入boss直聘

        long time= System.currentTimeMillis();
            while (true){
                action.longPress(PointOption.point(800,1000)).moveTo(PointOption.point(800,588)).release().perform();//滑动屏幕
                Thread.sleep(3000);
                driver.manage().timeouts().implicitlyWait(8, TimeUnit.SECONDS);
                driver.findElementById("com.hpbr.bosszhipin:id/ll_center").click();//点击定位的元素，这里是点击招聘信息
                Thread.sleep(3000);
                driver.manage().timeouts().implicitlyWait(8, TimeUnit.SECONDS);
                driver.findElementById("com.hpbr.bosszhipin:id/btn_chat").click();//点击立即沟通
                Thread.sleep(3000);
                driver.navigate().back();//返回
                Thread.sleep(3000);
                driver.navigate().back();//返回
                Thread.sleep(3000);
                //每20分钟刷新一次从头开始
                if (System.currentTimeMillis()>time+20*60*1000){
                    time=System.currentTimeMillis()+20*60*1000;
                    driver.findElementById("com.hpbr.bosszhipin:id/iv_tab_1").click();//点击左下角职位就可以刷新了
                    Thread.sleep(9000);
                }
            }
    }

    /*{
        "appPackage": "com.huawei.android.launcher",
            "appActivity": "com.huawei.android.launcher.unihome.UniHomeLauncher",
            "deviceName": "DWT7N18530002278",
            "automationName": "Appium",
            "platformName": "Android"
    }*/

    /**
     * java执行cmd命令，有兴趣可以了解，万一有场景需要用上
     */
    @Test
    public  void execCommand() {
        try {
            Runtime runtime = Runtime.getRuntime();
            // 打开任务管理器，exec方法调用后返回 Process 进程对象
            Process process = runtime.exec("cmd.exe /c cd C:/Users/JOJO/AppData/Local/Android/android-sdk/platform-tools&&adb shell");
            // 等待进程对象执行完成，并返回“退出值”，0 为正常，其他为异常
            int exitValue = process.waitFor();
            System.out.println("exitValue: " + exitValue);
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }
    }
}
