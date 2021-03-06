package Operation;

import Model.Account;

import java.util.List;

public class Check extends Operation {

    public Check(List<Account> accounts) {

        this.operation = () -> {

            for (Account account: accounts) {
                int expectedSum = 0, actualSum = 0;

                account.getLock().lock();

                // get initial balance
                expectedSum += account.getInitialBalance();

                // compute actual balance based on current balance and the transfer logs
                actualSum += account.getBalance();
                actualSum += account
                        .getLogs()
                        .stream()
                        .mapToInt(l -> l.getFrom() == account.getId() ? l.getAmount() : -l.getAmount())
                        .sum();

                if (expectedSum != actualSum) {
                    System.err.println("Expected balance and actual balance does not match for account: " + account.getId());
                }

                // check if the logs match
                account.getLogs()
                        .forEach(log -> {
                            int otherId = log.getFrom() == account.getId() ? log.getTo() : log.getFrom();
                            Account otherAccount = accounts
                                    .stream()
                                    .filter(a -> a.getId() == otherId)
                                    .findFirst()
                                    .orElse(null);

                            if (otherAccount == null) {
                                System.err.println("Invalid log in account: " + account.getId());
                            }
                            else {
                                boolean exists = otherAccount
                                        .getLogs()
                                        .stream()
                                        .anyMatch(l -> l.equals(log));

                                if (!exists) {
                                    System.err.println("Mirror log not found for account: " + account.getId());
                                }
                            }
                        });
            }

            for (Account account: accounts) {
                account.getLock().unlock();
            }
        };
    }
}
