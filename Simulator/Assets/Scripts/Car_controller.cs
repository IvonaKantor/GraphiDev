using UnityEngine;

public class Car_controller : MonoBehaviour
{

    [SerializeField] private Transform _transformFL;
    [SerializeField] private Transform _transformFR;
    [SerializeField] private Transform _transformBL;
    [SerializeField] private Transform _transformBR;

    [SerializeField] private WheelCollider _colliderFL;
    [SerializeField] private WheelCollider _colliderFR;
    [SerializeField] private WheelCollider _colliderBL;
    [SerializeField] private WheelCollider _colliderBR;

    [SezializeField] private float _force;
    [SezializeField] private float _maxAngle;

    private void FixedUpdate()
    {
        _colliderFL.motorTorgue = Input.GetAxis("Vertical") * _force;
        _colliderFR.motorTorgue = Input.GetAxis("Vertical") * _force;

        if (Input.GetKey(KeyCode.Space))
        { 
            
        }
    }
}
