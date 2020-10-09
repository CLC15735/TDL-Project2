package com.qa.ToDoList.selenium;

import static org.junit.Assert.assertEquals;

import java.util.concurrent.TimeUnit;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;

public class TestNewUpdateTasks {
	
private static WebDriver driver;
	
	@BeforeAll
	public static void setup() {
	System.setProperty("webdriver.chrome.driver", "C:\\Users\\e13cr\\Documents\\Training\\Project-2\\ToDoList\\src\\test\\resources\\driver\\chromedriver.exe");
	        driver = new ChromeDriver();
	        driver.manage().window().setSize(new Dimension(1366, 768));
	        
	        driver.manage().timeouts().pageLoadTimeout(2, TimeUnit.SECONDS);
	        driver.manage().timeouts().implicitlyWait(2, TimeUnit.SECONDS);
	}
	
	@Test
	public void test() throws InterruptedException {
		driver.get("http://127.0.0.1:5500/html/index.html");
		
		WebElement support;
		
		String title = "Shopping";
		String description = "Groceries";
		
		support = driver.findElement(By.xpath("/html/body/div[1]/div/h1/a"));
		support.click();
		support = driver.findElement(By.xpath("/html/body/div[2]/input"));
		support.sendKeys(title);
		support = driver.findElement(By.xpath("/html/body/div[3]/input"));
		support.sendKeys(description);
		support = driver.findElement(By.xpath("/html/body/button"));
		support.click();
		support = driver.findElement(By.xpath("/html/body/a"));
		support.click();
		Thread.sleep(1000);
		driver.navigate().refresh();
		support = driver.findElement(By.xpath("/html/body/div[1]/div/h1/a"));
		support.click();
		support = driver.findElement(By.xpath("/html/body/div[2]/input"));
		support.sendKeys("To Do");
		support = driver.findElement(By.xpath("/html/body/div[3]/input"));
		support.sendKeys("This week chores");
		support = driver.findElement(By.xpath("/html/body/button"));
		support.click();
		support = driver.findElement(By.xpath("/html/body/a"));
		support.click();
		Thread.sleep(1000);
		driver.navigate().refresh();
		
		support = driver.findElement(By.xpath("/html/body/div[2]/div[1]/div[2]/a"));
		support.click();
		support = driver.findElement(By.xpath("/html/body/div[2]/input"));
		support.sendKeys("Chocolate");
		support = driver.findElement(By.xpath("/html/body/div[3]/input"));
		support.sendKeys("high");
		support = driver.findElement(By.xpath("/html/body/button"));
		support.click();
		support = driver.findElement(By.xpath("/html/body/a"));
		support.click();
		Thread.sleep(1000);
		support = driver.findElement(By.xpath("/html/body/div[2]/div[1]/div[2]/ul/li/div/label"));
		String actual = support.getText();
		assertEquals("Chocolate", actual);
		
		support = driver.findElement(By.xpath("/html/body/div[2]/div[1]/div[2]/ul/li/div/a[1]"));
		support.click();
		Thread.sleep(1000);
		support = driver.findElement(By.xpath("/html/body/div/form/div[2]/input"));
		support.clear();
		support.sendKeys("Milk chocolate");
		support = driver.findElement(By.xpath("/html/body/div/form/button"));
		support.click();
		support = driver.findElement(By.xpath("/html/body/div/form/a"));
		support.click();
		support = driver.findElement(By.xpath("/html/body/div[2]/div[1]/div[2]/ul/li/div/label"));
		String actual2 = support.getText();
		assertEquals("Milk chocolate", actual2);
		
	}
	
	@AfterEach
	public void tearDown() {
        driver.close();
    }


}
