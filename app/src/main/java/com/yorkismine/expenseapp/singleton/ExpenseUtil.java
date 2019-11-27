package com.yorkismine.expenseapp.singleton;

import com.yorkismine.expenseapp.R;
import com.yorkismine.expenseapp.model.TypeOfExpense;

import java.util.ArrayList;

public class ExpenseUtil {
    private static ArrayList<TypeOfExpense> types;

    static {
        types = new ArrayList<>();
        types.add(new TypeOfExpense("Animal", R.drawable.ic_animal));
        types.add(new TypeOfExpense("Home", R.drawable.ic_home));
        types.add(new TypeOfExpense("Games", R.drawable.ic_games));
        types.add(new TypeOfExpense("Food", R.drawable.ic_food));
        types.add(new TypeOfExpense("Waste", R.drawable.ic_waste));
        types.add(new TypeOfExpense("Party", R.drawable.ic_party));
        types.add(new TypeOfExpense("Friends", R.drawable.ic_friends));
    }

    private ExpenseUtil() {

    }

    public static ArrayList<TypeOfExpense> getTypes() {
        return types;
    }

    public static TypeOfExpense getType(String typeText) {
        for (TypeOfExpense type : types) {
            if (type.getType().equals(typeText)) {
                return type;
            }
        }

        return null;
    }

}
