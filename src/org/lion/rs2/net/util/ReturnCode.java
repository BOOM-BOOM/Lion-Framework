package org.lion.rs2.net.util;

/**
 * All returns that can be sent during login.
 * 
 * @author BOOM BOOM (bamboombamboom@hotmail.com)
 */
public class ReturnCode {

	/**
	 * The initial return code.
	 */
	private static final byte INITIAL_CODE = 0;

	/**
	 * The login return code.
	 */
	private static final byte LOGIN = 2;

	/**
	 * The invalid details return code.
	 */
	private static final byte INVALID_DETAILS = 3;

	/**
	 * The banned return code.
	 */
	private static final byte BANNED = 4;

	/**
	 * The already logged in return code.
	 */
	private static final byte ALREADY_LOGGED_IN = 5;

	/**
	 * The server updated return code.
	 */
	private static final byte SERVER_UPDATED = 6;

	/**
	 * The world full return code.
	 */
	private static final byte WORLD_FULL = 7;

	/**
	 * The login server offline return code.
	 */
	private static final byte LOGIN_SERVER_OFFLINE = 8;

	/**
	 * The too many addresses return code.
	 */
	private static final byte TOO_MANY_ADDRESSES = 9;

	/**
	 * The bad session id return code.
	 */
	private static final byte BAD_SESSION_ID = 10;

	/**
	 * The login server rejection return code.
	 */
	private static final byte LOGIN_SERVER_REJECTION = 11;

	/**
	 * The members account needed return code.
	 */
	private static final byte MEMBERS_ACCOUNT_NEEDED = 12;

	/**
	 * The could not complete login return code.
	 */
	private static final byte COULD_NOT_COMPLETE_LOGIN = 13;

	/**
	 * The server being updated return code.
	 */
	private static final byte SERVER_BEING_UPDATED = 14;

	/**
	 * The login attempts exceeded return code.
	 */
	private static final byte LOGIN_ATTEMPTS_EXCEEDED = 16;

	/**
	 * The members area only return code.
	 */
	private static final byte MEMBERS_AREA_ONLY = 17;

	/**
	 * The invalid login server return code.
	 */
	private static final byte INVALID_LOGIN_SERVER = 18;

	/**
	 * The just left another world return code.
	 */
	private static final byte JUST_LEFT_ANOTHER_WORLD = 19;

	/**
	 * The character files are corrupt return code.
	 */
	private static final byte CHARACTER_FILES_CORRUPT = 20;

	/**
	 * The permanantly banned return code.
	 */
	private static final byte PERMANANTLY_BANNED = 21;

	/**
	 * The credentials too short return code.
	 */
	private static final byte CREDENTIALS_TOO_SHORT = 22;

	/**
	 * Gets the initial return code.
	 * 
	 * @return The initial return code.
	 */
	public static byte getInitialCode() {
		return INITIAL_CODE;
	}

	/**
	 * Gets the login return code.
	 * 
	 * @return The login return code.
	 */
	public static byte getLogin() {
		return LOGIN;
	}

	/**
	 * Gets the invalid details return code.
	 * 
	 * @return The invalid details return code.
	 */
	public static byte getInvalidDetails() {
		return INVALID_DETAILS;
	}

	/**
	 * Gets the banned return code.
	 * 
	 * @return The banned return code.
	 */
	public static byte getBanned() {
		return BANNED;
	}

	/**
	 * Gets the already logged in return code.
	 * 
	 * @return The already logged in return code.
	 */
	public static byte getAlreadyLoggedIn() {
		return ALREADY_LOGGED_IN;
	}

	/**
	 * Gets the server updated return code.
	 * 
	 * @return The server updated return code.
	 */
	public static byte getServerUpdated() {
		return SERVER_UPDATED;
	}

	/**
	 * Gets the world full return code.
	 * 
	 * @return The world full return code.
	 */
	public static byte getWorldFull() {
		return WORLD_FULL;
	}

	/**
	 * Gets the login server offline return code.
	 * 
	 * @return The login server offline return code.
	 */
	public static byte getLoginServerOffline() {
		return LOGIN_SERVER_OFFLINE;
	}

	/**
	 * Gets the too many addresses return code.
	 * 
	 * @return The too many addresses return code.
	 */
	public static byte getTooManyAddresses() {
		return TOO_MANY_ADDRESSES;
	}

	/**
	 * Gets the bad session id return code.
	 * 
	 * @return The bad session id return code.
	 */
	public static byte getBadSessionID() {
		return BAD_SESSION_ID;
	}

	/**
	 * Gets the login server rejection return code.
	 * 
	 * @return The login server rejection return code.
	 */
	public static byte getLoginServerRejection() {
		return LOGIN_SERVER_REJECTION;
	}

	/**
	 * Gets the members account needed return code.
	 * 
	 * @return The members account needed return code.
	 */
	public static byte getMembersAccountNeeded() {
		return MEMBERS_ACCOUNT_NEEDED;
	}

	/**
	 * Gets the could not complete login return code.
	 * 
	 * @return The could not complete login return code.
	 */
	public static byte getCouldNotCompleteLogin() {
		return COULD_NOT_COMPLETE_LOGIN;
	}

	/**
	 * Gets the server being updated return code.
	 * 
	 * @return The server being updated return code.
	 */
	public static byte getServerBeingUpdated() {
		return SERVER_BEING_UPDATED;
	}

	/**
	 * Gets the login attempts exceeded return code.
	 * 
	 * @return The login attempts exceeded return code.
	 */
	public static byte getLoginAttemptsExceeded() {
		return LOGIN_ATTEMPTS_EXCEEDED;
	}

	/**
	 * Gets the members area only return code.
	 * 
	 * @return The members area only return code.
	 */
	public static byte getMembersAreaOnly() {
		return MEMBERS_AREA_ONLY;
	}

	/**
	 * Gets the invalid login server return code.
	 * 
	 * @return The invalid login server return code.
	 */
	public static byte getInvalidLoginServer() {
		return INVALID_LOGIN_SERVER;
	}

	/**
	 * Gets the just left another world return code.
	 * 
	 * @return The just left another world return code.
	 */
	public static byte getJustLeftAnotherWorld() {
		return JUST_LEFT_ANOTHER_WORLD;
	}

	/**
	 * Gets the character files are corrupt return code.
	 * 
	 * @return The charracter files are corrupt return code.
	 */
	public static byte getCharacterFilesCorrupt() {
		return CHARACTER_FILES_CORRUPT;
	}

	/**
	 * Gets the permanantly banned return code.
	 * 
	 * @return The permanantly banned return code.
	 */
	public static byte getPermanantlyBanned() {
		return PERMANANTLY_BANNED;
	}

	/**
	 * Gets the credentials too short return code.
	 * 
	 * @return The credentials too short return code.
	 */
	public static byte getCredentialsTooShort() {
		return CREDENTIALS_TOO_SHORT;
	}

}