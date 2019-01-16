package com.tcs.ifact.helper;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.DayOfWeek;
import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.Month;
import java.time.YearMonth;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import org.apache.commons.codec.binary.Base64;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import com.tcs.ifact.dao.IUtilDao;
import com.tcs.ifact.dao.Impl.UtilDaoImpl;
import com.tcs.ifact.model.Leakage;
import com.tcs.ifact.model.Util;


public class IFactHelper {

	public static boolean isNumeric(String str) {
		boolean result = false;
		try {
			Integer.parseInt(str);
			result = true;
		}catch(Exception e) {
			result = false;
		}
		return result;
	}


	public static String deriveMonthDiff(String monthMax, String monthAct) {
		String diff = null;
		if(null != monthMax && null != monthAct) {
			int a= Integer.parseInt(monthMax);
			int b= Integer.parseInt(monthAct);

			int c= a-b;

			diff  = String.valueOf(c);
		}
		return diff;
	}
	
/*	public static String calculateLeakageRevenue(String leakageHrs, String cbr) {
		double a= Double.parseDouble(leakageHrs);
		double b= Double.parseDouble(cbr);
		double lr = a * b;
		String lrs  = String.valueOf(lr);
		return lrs;
	}*/



	public static String getDateInString(Date date) {
		String DATE_FORMAT = "dd-MM-yyy";
		//DateTimeFormatter formatter = DateTimeFormatter.ofPattern(DATE_FORMAT);
		//Instant instant = date.toInstant();
		//LocalDateTime ldt = instant.atZone(ZoneId.of("CET")).toLocalDateTime();
		DateFormat dateFormat = new SimpleDateFormat(DATE_FORMAT);
		String strDate = dateFormat.format(date);
		//String dateString = instant.toString();
		return strDate;
	}

	public static String calculateHrsRev(String currentBillRate, String leakageHrs, String dollerRate) {
		String rev = null;
		BigDecimal br = null;
		BigDecimal lh = null;
		BigDecimal dr = null;
		BigDecimal revD = null;
		
		if(null != currentBillRate && !currentBillRate.isEmpty()) {
			br = new BigDecimal(currentBillRate);
		}

		if(null != leakageHrs && !leakageHrs.isEmpty()) {
			lh = new BigDecimal(leakageHrs);
		}

		if(null != dollerRate && !dollerRate.isEmpty()) {
			dr = new BigDecimal(dollerRate);
		}
		

		if(null != br) {
			revD = br;
			if(null != lh) {
				revD = revD.multiply(lh);
				if(null != dr) {
					revD = revD.multiply(dr);
				}
			}else if(null != dr) {
				revD = revD.multiply(dr);
			}
		}else if(null != lh){
			revD = lh;
			if(null != dr) {
				revD = revD.multiply(dr);
			}
		}else if(null != dr) {
			revD = dr;
		}

		if(null != revD) {
			rev = revD.toString();
		}
		
		return rev;
	}

	public static String calculateMonthRev(String monthLeaveRev, String monthrDwReplRev, String monthrDwoReplRev,
			String monthParkingRev, String monthMovmtRev, String monthOthersRev) {
		String monthrev = null;
		BigDecimal revD = null;
		BigDecimal mL = null;
		BigDecimal mRwR = null;
		BigDecimal mRwoR = null;
		BigDecimal mP = null;
		BigDecimal mM =null;
		BigDecimal mO =null;

		if(null != monthLeaveRev && !monthLeaveRev.isEmpty()) {
			mL = new BigDecimal(monthLeaveRev);
		}
		
		if(null != monthrDwReplRev && !monthrDwReplRev.isEmpty()) {
			mRwR = new BigDecimal(monthrDwReplRev);
		}
		
		if(null != monthrDwoReplRev && !monthrDwoReplRev.isEmpty() ) {
			mRwoR = new BigDecimal(monthrDwoReplRev);
		}
		
		if(null != monthParkingRev && !monthParkingRev.isEmpty()) {
			mP = new BigDecimal(monthParkingRev);
		}
		
		if(null != monthMovmtRev && !monthMovmtRev.isEmpty()) {
			mM = new BigDecimal(monthMovmtRev);
		}
		
		if(null != monthOthersRev && !monthOthersRev.isEmpty()) {
			mO = new BigDecimal(monthOthersRev);
		}
		
		if(null != mL) {
			revD = mL;
			if(null != mRwR) {
				revD = revD.add(mRwR);
				if(null != mRwoR) {
					revD = revD.add(mRwoR);
					if(null != mP) {
						revD = revD.add(mP);
						if(null != mM) {
							revD = revD.add(mM);
							if(null != mO) {
								revD = revD.add(mO);
							}
						}else if(null != mO) {
							revD = revD.add(mO);
						}
					}else if(null != mM) {
						revD = revD.add(mM);
						if(null != mO) {
							revD = revD.add(mO);
						}
					}else if(null != mO) {
						revD = revD.add(mO);
					}
				}else if(null != mP) {
					revD = revD.add(mP);
					if(null != mM) {
						revD = revD.add(mM);
						if(null != mO) {
							revD = revD.add(mO);
						}
					}else if(null != mO) {
						revD = revD.add(mO);
					}
				}
			}else if(null != mRwoR) {
				revD = revD.add(mRwoR);
				if(null != mP) {
					revD = revD.add(mP);
					if(null != mM) {
						revD = revD.add(mM);
						if(null != mO) {
							revD = revD.add(mO);
						}
					}else if(null != mO) {
						revD = revD.add(mO);
					}
				}else if(null != mM) {
					revD = revD.add(mM);
					if(null != mO) {
						revD = revD.add(mO);
					}
				}else if(null != mO) {
					revD = revD.add(mO);
				}
			}else if(null != mP) {
				revD = revD.add(mP);
				if(null != mM) {
					revD = revD.add(mM);
					if(null != mO) {
						revD = revD.add(mO);
					}
				}else if(null != mO) {
					revD = revD.add(mO);
				}
			}else if(null != mM){
				revD = revD.add(mM);
				if(null != mO) {
					revD = revD.add(mO);
				}
			}else if(null != mO) {
				revD = revD.add(mO);
			}
		}else if(null != mRwR) {
			revD = mRwR;
			if(null != mRwoR) {
				revD = revD.add(mRwoR);
				if(null != mP) {
					revD = revD.add(mP);
					if(null != mM) {
						revD = revD.add(mM);
						if(null != mO) {
							revD = revD.add(mO);
						}
					}else if(null != mO) {
						revD = revD.add(mO);
					}
				}else if(null != mM) {
					revD = revD.add(mM);
					if(null != mO) {
						revD = revD.add(mO);
					}
				}else if(null != mO) {
					revD = revD.add(mO);
				}
			}else if(null != mP) {
				revD = revD.add(mP);
				if(null != mM) {
					revD = revD.add(mM);
					if(null != mO) {
						revD = revD.add(mO);
					}
				}else if(null != mO) {
					revD = revD.add(mO);
				}
			}
		}else if(null != mRwoR) {
			revD = mRwoR;
			if(null != mP) {
				revD = revD.add(mP);
				if(null != mM) {
					revD = revD.add(mM);
					if(null != mO) {
						revD = revD.add(mO);
					}
				}else if(null != mO) {
					revD = revD.add(mO);
				}
			}else if(null != mM) {
				revD = revD.add(mM);
				if(null != mO) {
					revD = revD.add(mO);
				}
			}else if(null != mO) {
				revD = revD.add(mO);
			}
		}else if(null != mP) {
			revD = mP;
			if(null != mM) {
				revD = revD.add(mM);
				if(null != mO) {
					revD = revD.add(mO);
				}
			}else if(null != mO) {
				revD = revD.add(mO);
			}
		}else if(null != mM) {
			revD = mM;
			if(null != mO) {
				revD = revD.add(mO);
			}
		}else if(null != mO) {
			revD = mO;
		}

		if(null != revD) {
			monthrev = revD.toString();
		}
		return monthrev;
	}

	public static String calculateRevInDoller(String revINR, String dollerRate) {
		String monthRev = null;
		
		BigDecimal mRev = null;
		BigDecimal revBDINR = null;
		BigDecimal dR = null;
		
		if(null != revINR) {
			revBDINR = new BigDecimal(revINR);
		}
		
		if(null != dollerRate) {
			dR = new BigDecimal(dollerRate);
		}
		
		if(null != revBDINR) {
			mRev = revBDINR;
			if(null != dR) {
				mRev = mRev.divide(dR, 4, RoundingMode.CEILING);
			}
		}
		
		if(null != mRev) {
			monthRev = mRev.toString();
		}

		return monthRev;
	}

	public static String getCurrentMonth() {
		ZoneId zoneId = ZoneId.of ("Asia/Kolkata");
		LocalDate today = LocalDate.now ( zoneId );
		return today.getMonth().toString();
		
	}

	public static String getQuarterTotal(String month1RevINR, String month2RevINR, String month3RevINR) {
		String rev = null;
		BigDecimal m1 = null;
		BigDecimal m2 = null;
		BigDecimal m3 = null;
		BigDecimal revD = null;
		
		if(null != month1RevINR && !month1RevINR.isEmpty()) {
			m1 = new BigDecimal(month1RevINR);
		}

		if(null != month2RevINR && !month2RevINR.isEmpty()) {
			m2 = new BigDecimal(month2RevINR);
		}

		if(null != month3RevINR && !month3RevINR.isEmpty()) {
			m3 = new BigDecimal(month3RevINR);
		}
		

		if(null != m1) {
			revD = m1;
			if(null != m2) {
				revD = revD.add(m2);
				if(null != m3) {
					revD = revD.add(m3);
				}
			}else if(null != m3) {
				revD = revD.add(m3);
			}
		}else if(null != m2){
			revD = m2;
			if(null != m3) {
				revD = revD.add(m3);
			}
		}else if(null != m3) {
			revD = m3;
		}

		if(null != revD) {
			rev = revD.toString();
		}
		
		return rev;
	}

	public static String add2String(String rev, String in) {
		String monthRev = null;
		
		BigDecimal mRev = null;
		BigDecimal revBDINR = null;
		BigDecimal inBD = null;
		
		if(null != rev) {
			revBDINR = new BigDecimal(rev);
		}
		
		if(null != in) {
			inBD = new BigDecimal(in);
		}
		
		if(null != revBDINR) {
			mRev = revBDINR;
			if(null != inBD) {
				mRev = mRev.add(inBD);
			}
		}else if(null != inBD) {
			mRev = inBD;
		}
		
		if(null != mRev) {
			monthRev = mRev.toString();
		}

		return monthRev;
	}

	public static String genBase64Auth(String user, String password) {
		String auth = null;
		if(null!=user && !user.isEmpty() && null != password && !password.isEmpty()) {
			String authString = user + ":" + password;
			byte[] authEncBytes = Base64.encodeBase64(authString.getBytes());
			String authStringEnc = new String(authEncBytes);
			auth = "Basic " + authStringEnc;
		}
		return auth;
	}


	public static ArrayList stringToArray(String data) {
		List<String> dataList = new ArrayList();
		if(null != data && !data.isEmpty()) {
			String[] dataArry = data.split(";");
			dataList = Arrays.asList(dataArry);
		}
		return (ArrayList) dataList;
	}
	



}
