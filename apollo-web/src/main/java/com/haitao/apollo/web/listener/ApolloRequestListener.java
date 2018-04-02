package com.haitao.apollo.web.listener;

import javax.servlet.ServletRequestEvent;
import javax.servlet.ServletRequestListener;

import com.haitao.apollo.plugin.session.SessionContainer;

public class ApolloRequestListener implements ServletRequestListener {

	@Override
	public void requestInitialized(ServletRequestEvent arg0) {
		SessionContainer.setSession(null);
	}

	@Override
	public void requestDestroyed(ServletRequestEvent arg0) {
		SessionContainer.setSession(null);
	}

}
