package com.example.ivanwl.specifit.Services.Nutritionix.Models.Search;

import androidx.annotation.Nullable;

public class Hit {
    public String _index;
    public String _type;
    public String _id;
    public double _score;
    public Field fields;

    public boolean equals(@Nullable Hit obj) {
        if (obj == null)
            return false;
        return this._id.equals(obj._id);
    }
}
