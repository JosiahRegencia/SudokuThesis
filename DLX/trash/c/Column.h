#include "Node.h"

class Column : public Node
{
	protected:
		int size;
		char *label;
	public:
		Column(char *l)	: Node(), label(l), size(0) {}
};


