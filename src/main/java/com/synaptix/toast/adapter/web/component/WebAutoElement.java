package com.synaptix.toast.adapter.web.component;

import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import com.synaptix.toast.adapter.web.ISyncCall;
import com.synaptix.toast.automation.driver.web.SynchronizedDriver;
import com.synaptix.toast.core.runtime.IFeedableWebPage;
import com.synaptix.toast.core.runtime.IWebAutoElement;
import com.synaptix.toast.core.runtime.IWebElementDescriptor;

public class WebAutoElement implements IWebAutoElement<WebElement>{

	protected IFeedableWebPage container;

	protected IWebElementDescriptor descriptor;

	protected SynchronizedDriver<WebElement, ?> frontEndDriver;

	protected void setDescriptor(
		IWebElementDescriptor wrappedElement) {
		this.descriptor = wrappedElement;
	}

	protected SynchronizedDriver<WebElement, ?> getFrontEndDriver() {
		return frontEndDriver;
	}

	public void setDriver(
			SynchronizedDriver frontEndDriver) {
		this.frontEndDriver = frontEndDriver;
	}

	public WebAutoElement(
		IWebElementDescriptor element,
		SynchronizedDriver driver) {
		this.descriptor = element;
		this.frontEndDriver = driver;
	}

	public WebAutoElement(
		IWebElementDescriptor element) {
		this.descriptor = element;
	}

	public WebAutoElement() {
	}

	public boolean exists() {
		return frontEndDriver.find(descriptor) != null;
	}

	@Override
	public WebElement getWebElement() {
		if(container != null && container.getDescriptor() != null){ //push error if it's not css
			By cssSelector = By.cssSelector(this.descriptor.getLocator());
			WebElement findElement = ((WebElement)container.getWebElement()).findElement(cssSelector);
			return findElement;
		}
		return frontEndDriver.find(descriptor);
	}
	
	@Override
	public List<WebElement> getAllWebElements() {
		return frontEndDriver.findAll(descriptor);
	}

	protected void safeAction(ISyncCall res) {
		WebElement find = frontEndDriver.find(descriptor);
		if(find != null) {
			res.execute(find);
		}
	}

	@Override
	public IWebElementDescriptor getDescriptor() {
		return descriptor;
	}

	@Override
	public IFeedableWebPage getContainer() {
		return container;
	}

	@Override
	public void setContainer(IFeedableWebPage container) {
		this.container = container;
	}

	@Override
	public List<IWebElementDescriptor> getChildren() {
		// NO OP ?
		return null;
	}

}
