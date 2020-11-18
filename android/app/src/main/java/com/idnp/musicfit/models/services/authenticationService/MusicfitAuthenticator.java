package com.idnp.musicfit.models.services.authenticationService;

import android.accounts.AbstractAccountAuthenticator;
import android.accounts.Account;
import android.accounts.AccountAuthenticatorResponse;
import android.accounts.AccountManager;
import android.accounts.NetworkErrorException;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.TextUtils;

import com.idnp.musicfit.models.services.musicfitPreferences.MusicfitPreferencesService;
import com.idnp.musicfit.views.activities.loginView.LoginActivity;

public class MusicfitAuthenticator extends AbstractAccountAuthenticator {

    private Context context;

    public MusicfitAuthenticator(Context context) {
        super(context);
    }

    @Override
    public Bundle editProperties(AccountAuthenticatorResponse response, String accountType) {
        return null;
    }

    @Override
    public Bundle addAccount(AccountAuthenticatorResponse response, String accountType, String authTokenType, String[] requiredFeatures, Bundle options) throws NetworkErrorException {
        final Intent intent = new Intent(context, LoginActivity.class);
        intent.putExtra(MusicfitAuthenticationManagerService.ACCOUNT_TYPE, accountType);
        intent.putExtra(MusicfitAuthenticationManagerService.AUTH_TOKEN_TYPE_KEY, authTokenType);
        //intent.putExtra("is_adding_new_account", true);
        intent.putExtra(AccountManager.KEY_ACCOUNT_AUTHENTICATOR_RESPONSE, response);

        final Bundle bundle = new Bundle();
        bundle.putParcelable(AccountManager.KEY_INTENT, intent);

        return bundle;
    }

    @Override
    public Bundle confirmCredentials(AccountAuthenticatorResponse response, Account account, Bundle options) throws NetworkErrorException {
        return null;
    }

    @Override
    public Bundle getAuthToken(AccountAuthenticatorResponse response, Account account, String authTokenType, Bundle options) throws NetworkErrorException {
        AccountManager am = AccountManager.get(this.context);

        String authToken = am.peekAuthToken(account, authTokenType);

        if (TextUtils.isEmpty(authToken)) {
            SharedPreferences preferences = MusicfitPreferencesService.musicfitPreferencesService.openSharedPreferencesFile(MusicfitAuthenticationManagerService.PREFERENCES_FILE);
            authToken = MusicfitPreferencesService.musicfitPreferencesService.readPreference(preferences, MusicfitAuthenticationManagerService.PREFERENCES_TOKEN_KEY);
        }

        if (!TextUtils.isEmpty(authToken)) {
            final Bundle result = new Bundle();
            result.putString(AccountManager.KEY_ACCOUNT_NAME, account.name);
            result.putString(AccountManager.KEY_ACCOUNT_TYPE, account.type);
            result.putString(AccountManager.KEY_AUTHTOKEN, authToken);
            return result;
        }

        final Intent intent = new Intent(this.context, LoginActivity.class);
        intent.putExtra(AccountManager.KEY_ACCOUNT_AUTHENTICATOR_RESPONSE, response);
        intent.putExtra(MusicfitAuthenticationManagerService.ACCOUNT_TYPE, account.type);
        intent.putExtra(MusicfitAuthenticationManagerService.AUTH_TOKEN_TYPE_KEY, authTokenType);

        Bundle retBundle = new Bundle();
        retBundle.putParcelable(AccountManager.KEY_INTENT, intent);
        return retBundle;
    }

    @Override
    public String getAuthTokenLabel(String authTokenType) {
        return null;
    }

    @Override
    public Bundle updateCredentials(AccountAuthenticatorResponse response, Account account, String authTokenType, Bundle options) throws NetworkErrorException {
        return null;
    }

    @Override
    public Bundle hasFeatures(AccountAuthenticatorResponse response, Account account, String[] features) throws NetworkErrorException {
        return null;
    }
}
