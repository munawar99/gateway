package com.jpmorgan.asset.gateway;

import java.math.BigDecimal;

public interface Service {
	BigDecimal getPrice(String name);
}