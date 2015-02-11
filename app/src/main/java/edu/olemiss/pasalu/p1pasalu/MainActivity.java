/**
 * Programmer: Peter Salu
 * Created on: February 3, 2015
 * Description: A simple tip calculator.
 * Honor Code Statement: In keeping with the School of Engineering honor code, this project
 * represents my individual work.
 */

package edu.olemiss.pasalu.p1pasalu;

import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.NumberPicker;
import android.widget.SeekBar;
import android.widget.TextView;


public class MainActivity extends ActionBarActivity {
	private static final int ONE_PERSON = 1;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		setupTipPercent();
		setupNumberOfPeople();
	}


	private void setupTipPercent() {
		SeekBar tipPercentSeekBar = (SeekBar) findViewById(R.id.tipPercent);
		tipPercentSeekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
			@Override
			public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
				TextView tipPercentTextView = (TextView) findViewById(R.id.tipPercentText);
				CharSequence charSequenceProgress = Integer.toString(progress) + "%";
				tipPercentTextView.setText(charSequenceProgress);
			}

			@Override
			public void onStartTrackingTouch(SeekBar seekBar) {

			}

			@Override
			public void onStopTrackingTouch(SeekBar seekBar) {

			}
		});

		CharSequence initialProgress = Integer.toString(tipPercentSeekBar.getProgress()) + "%";
		TextView tipPercentTextView = (TextView) findViewById(R.id.tipPercentText);
		tipPercentTextView.setText(initialProgress);
	}

	private void setupNumberOfPeople() {
		NumberPicker numberOfPeople = (NumberPicker) findViewById(R.id.numberOfPeople);
		numberOfPeople.setMinValue(1);
		numberOfPeople.setMaxValue(99);

		numberOfPeople.setOnScrollListener(new NumberPicker.OnScrollListener() {
			@Override
			public void onScrollStateChange(NumberPicker view, int scrollState) {
				TextView people = (TextView) findViewById(R.id.numberOfPeopleText);

				if(view.getValue() == ONE_PERSON) {
					people.setText(R.string.person);
				} else {
					people.setText(R.string.people);
				}
			}
		});
	}

	public void onCalculateClick(View button) {
		EditText billEditText = (EditText) findViewById(R.id.bill);

		try {
			double bill = Double.parseDouble(billEditText.getText().toString());
			SeekBar tipPercentSeekBar = (SeekBar) findViewById(R.id.tipPercent);
			double tipPercent = (double)tipPercentSeekBar.getProgress() / 100;
			double tip = tipPercent * bill;
			double total = tip + bill;

			TextView tipAmountTextView = (TextView) findViewById(R.id.tipAmount);
			TextView totalTextView = (TextView) findViewById(R.id.total);
			NumberPicker numberOfPeopleNumberPicker =
					(NumberPicker) findViewById(R.id.numberOfPeople);

			int numberOfPeople = numberOfPeopleNumberPicker.getValue();

			String tipAmountString = getString(R.string.tip_amount);
			String totalString = getString(R.string.total);

			if(numberOfPeople != ONE_PERSON) {
				tip /= numberOfPeople;
				total /= numberOfPeople;

				totalString = getString(R.string.total_per_person);
			}

			tip = twoDecimalPlaces(tip);
			total = twoDecimalPlaces(total);

			tipAmountString += " ";
			totalString += " ";

			tipAmountTextView.setText(tipAmountString + Double.toString(tip));
			totalTextView.setText(totalString + Double.toString(total));
		} catch (NumberFormatException numberFormatException) {
			billEditText.setError(getString(R.string.bill_format_error));
		}
	}

	/**
	 * Rounds a number to two decimal places.
	 * @param number The number to be rounded.
	 * @return number rounded to two decimal places.
	 */
	//Taken from: http://stackoverflow.com/questions/11701399/round-up-to-2-decimal-places-in-java
	private double twoDecimalPlaces(double number) {
		return Math.round(number * 100.0) / 100.0;
	}


	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.menu_main, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();

		//noinspection SimplifiableIfStatement
		if (id == R.id.action_settings) {
			return true;
		}

		return super.onOptionsItemSelected(item);
	}
}
