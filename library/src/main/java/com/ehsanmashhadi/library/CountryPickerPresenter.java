package com.ehsanmashhadi.library;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

class CountryPickerPresenter implements CountryPickerContractor.Presenter {

    private CountryPickerContractor.View mView;
    private ICountryRepository mICountryRepository;
    private List<Country> mCountries;

    CountryPickerPresenter(ICountryRepository iCountryRepository, CountryPickerContractor.View view) {

        mICountryRepository = iCountryRepository;
        mView = view;
    }

    @Override
    public void getCountries(List<String> exceptCountries) {

        List<Country> countries = mICountryRepository.getCountries();
        if (exceptCountries.size() > 0) {
            countries = exceptCountriesByName(countries, exceptCountries);
        }
        mCountries = countries;
        mView.setCountries(countries);
    }

    private List<Country> exceptCountriesByName(List<Country> countries, List<String> exceptCountriesName) {

        for (String countryName : exceptCountriesName) {
            for (Country country : countries) {
                if (country.getName().toLowerCase().equals(countryName.toLowerCase())) {
                    countries.remove(country);
                    break;
                }
            }
        }
        return countries;
    }

    @Override
    public void filterSearch(String query) {

        List<Country> filteredCountries = new ArrayList<>();
        for (Country country : mCountries) {
            if (country.getName().contains(query)) {
                filteredCountries.add(country);
            }
        }
        mView.setCountries(filteredCountries);
    }

    @Override
    public void sort(CountryPicker.Sort sort) {

        switch (sort) {
            case CODE: {
                Comparator<Country> comparator = (country1, country2) -> country1.getCode().compareTo(country2.getCode());
                Collections.sort(mCountries, comparator);
                break;
            }

            case COUNTRY: {
                Comparator<Country> comparator = (country1, country2) -> country1.getName().compareTo(country2.getName());
                Collections.sort(mCountries, comparator);
                break;
            }
        }
    }
}