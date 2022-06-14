package se.xfunserver.mainlyckohjul.storage;

import se.xfunserver.mainlyckohjul.account.Account;
import se.xfunserver.mainlyckohjul.account.AccountField;

import java.util.Objects;
import java.util.UUID;
import java.util.function.BiConsumer;

public interface AccountStorage extends Storage {

    Account getAccount(UUID uuid) throws Exception;

    default void getAccount(UUID uuid, BiConsumer<Account, Exception> callback) {
        new Thread(() -> {
            try {
                Account account = getAccount(uuid);
                callback.accept(account, null);
            } catch (Exception exception) {
                callback.accept(null, exception);
            }
        }).start();
    }

    boolean storeAccount(Account account);

    void update(UUID uuid, AccountField field, Object value);

    Object get(UUID uuid, AccountField field);

    default void update(Account account, AccountField field, Object value) {
        update(account.getUniqueId(), field, value);
    }
}
