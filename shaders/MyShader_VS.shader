#version 430 core
layout(location = 0) in vec4 vertexPosition; //we can say vec4 even though we're sending vec3, graphics hardware will fill it in with ones
layout(location = 1) in vec3 vertexColor;
layout(location = 2) in vec3 vertexNormal;

out vec4 interpolatedVertexColor;
out vec3 theNormal;
out vec3 thePosition;
out vec3 theColor;

uniform mat4 MVPTransform;
uniform mat4 LightSpaceTransform;
uniform mat3 NormalTransform;
uniform vec3 LightPosition;
uniform mat4 ModelTransform;

void main()
{
	theColor = vertexColor;
	theNormal = vec3(ModelTransform * vec4(vertexNormal, 0)); //zero for eliminating translating factor from model to world tranformation matrix
	thePosition = vec3(ModelTransform * vertexPosition);
	gl_Position = MVPTransform * ModelTransform * vertexPosition;
}
