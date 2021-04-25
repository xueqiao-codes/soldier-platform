/**
  * Created By wileywang@2014-04-06
  *   for function name description extension
  */
#ifndef T_FUNCTION_NAME_WILEY
#define T_FUNCTION_NAME_WILEY


class t_function_name {
public:
	t_function_name(const std::string& name, int number = -1)
		: name_(name), number_(number)
	{}
	
	const std::string& get_name() const { return name_; }
	int get_number() const { return number_; }
	
	void set_number(int number) { number_ = number;}

private:
	std::string name_;
	int number_;
};




#endif
