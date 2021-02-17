package lotto.view;

import java.util.Arrays;
import java.util.Scanner;
import lotto.domain.Lotto;
import lotto.domain.LottoGroup;
import lotto.domain.LottoNumber;
import lotto.domain.Money;
import lotto.domain.WinningLotto;
import lotto.exception.LottoException;
import lotto.util.LottoGenerator;
import lotto.util.LottoSeller;
import lotto.util.RandomLottoStrategy;

public class InputView {

  private static final Scanner SCAN = new Scanner(System.in);
  private static final String INPUT_MONEY_MESSAGE = "구입금액을 입력해 주세요.";
  private static final String INPUT_WINNING_NUMBER_MESSAGE = "지난 주 당첨 번호를 입력해 주세요.";
  private static final String INPUT_BONUS_NUMBER_MESSAGE = "보너스 볼을 입력해 주세요.";

  public static LottoGroup randomLottoGroup() {
    try {
      Money money = money();
      LottoSeller lottoSeller = new LottoSeller();
      return lottoSeller.sellLotto(money, new RandomLottoStrategy());
    } catch (LottoException e) {
      OutputView.printMessage(e.getMessage());
      return randomLottoGroup();
    }
  }

  public static Money money() {
    try {
      OutputView.printMessage(INPUT_MONEY_MESSAGE);
      int money = Integer.parseInt(SCAN.nextLine());
      return Money.of(money);
    } catch (NumberFormatException e) {
      OutputView.printMessage("숫자를 입력해주세요.");
      return money();
    } catch (LottoException e) {
      OutputView.printMessage(e.getMessage());
      return money();
    }
  }

  public static WinningLotto winningLotto() {
    try {
      return new WinningLotto(winningNumbers(), bonusNumber());
    } catch (LottoException e) {
      OutputView.printMessage(e.getMessage());
      return winningLotto();
    }
  }

  private static Lotto winningNumbers() {
    try {
      OutputView.printMessage(INPUT_WINNING_NUMBER_MESSAGE);
      String winningNumbers = SCAN.nextLine();

      int[] numbers = Arrays
          .stream(winningNumbers.trim().split(","))
          .mapToInt(Integer::parseInt)
          .toArray();

      return LottoGenerator.generate(() -> LottoNumber.asList(numbers));
    } catch (LottoException e) {
      OutputView.printMessage(e.getMessage());
      return winningNumbers();
    } catch (NumberFormatException e) {
      OutputView.printMessage("숫자를 입력해주세요.");
      return winningNumbers();
    }
  }

  private static LottoNumber bonusNumber() {
    try {
      OutputView.printMessage(INPUT_BONUS_NUMBER_MESSAGE);
      return LottoNumber.of(Integer.parseInt(SCAN.nextLine()));
    } catch (LottoException e) {
      OutputView.printMessage(e.getMessage());
      return bonusNumber();
    }
  }
}
